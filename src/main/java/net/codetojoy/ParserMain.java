package net.codetojoy;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.*;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import net.codetojoy.data.*;
import net.codetojoy.message.*;
import net.codetojoy.util.*;

public class ParserMain extends AbstractBehavior<BeginProcessing> {

    private static DataSource dataSource;
    private static String outputCsvFilename;

    private Map<String,ActorRef<ParseRow>> parserMap = new HashMap<>();

    public static Behavior<BeginProcessing> create(String inputCsvFilename, String outputCsvFilename) {
        ParserMain.outputCsvFilename = outputCsvFilename;
        ParserMain.dataSource = DataSources.getProdDataSource(inputCsvFilename);
        return Behaviors.setup(ParserMain::new);
    }

    private ParserMain(ActorContext<BeginProcessing> context) {
        super(context);
    }

    @Override
    public Receive<BeginProcessing> createReceive() {
        return newReceiveBuilder().onMessage(BeginProcessing.class, this::onBeginProcessing).build();
    }

    private Behavior<BeginProcessing> onBeginProcessing(BeginProcessing command) {
        try {
            Timer timer = new Timer();
            final String commandName = command.name;
            final ActorRef<EmitCase> replyTo = getContext().spawn(Emitter.create(outputCsvFilename), commandName);
            final CaseIdCursor caseIdCursor = new CaseIdCursor();

            Stream<String> dataInfoStream = dataSource.getData();
            dataInfoStream.forEach(dataInfoString -> processDataInfo(dataInfoString, caseIdCursor, commandName, replyTo));

            // finish off the last boundary
            sendDoneMessage(caseIdCursor.lastCaseId, command.name, replyTo);

            getContext().getLog().info("TRACER ParserMain {}", timer.getElapsed("messages sent"));
        } catch (Exception ex) {
            getContext().getLog().error("TRACER ParserMain caught exception! ex: {}", ex.getMessage());
        }

        return this;
    }

    protected void processDataInfo(String dataInfoString, CaseIdCursor caseIdCursor, String commandName, ActorRef<EmitCase> replyTo) {
        DataInfo dataInfo = dataSource.getDataInfo(dataInfoString);

        // TODO: filter by region ?
        RowFilter rowFilter = DataSources.getRowFilter();
        boolean doInclude = (! rowFilter.doExclude(dataInfo));

        if (doInclude) {
            String caseId = dataInfo.caseId;

            getContext().getLog().info("TRACER ParserMain lastCaseId: {} caseId: {}", caseIdCursor.lastCaseId, caseId);

            if (caseIdCursor.lastCaseId == null) {
                caseIdCursor.lastCaseId = caseId;
            }

            if (! caseId.equals(caseIdCursor.lastCaseId)) {
                // new boundary, so done
                sendDoneMessage(caseIdCursor.lastCaseId, commandName, replyTo);
                caseIdCursor.lastCaseId = caseId;
            }

            sendMessage(caseId, dataInfo.payload, commandName, replyTo);
        } else {
            getContext().getLog().error("TRACER illegal caseId: {}", dataInfo.caseId);
        }
    }

    protected ActorRef<ParseRow> getGreeterByCaseId(String caseId) {
        ActorRef<ParseRow> parser = null;

        if (parserMap.keySet().contains(caseId)) {
            parser = parserMap.get(caseId);
        } else {
            parser = getContext().spawn(Parser.create(), Constants.ACTOR_NAME_PREFIX + caseId);
            parserMap.put(caseId, parser);
        }

        return parser;
    }

    protected void sendDoneMessage(String caseId, String name, ActorRef<EmitCase> replyTo) {
        String payload = "";
        boolean isDone = true;
        ActorRef<ParseRow> parser = getGreeterByCaseId(caseId);
        parser.tell(new ParseRow(caseId, payload, isDone, name, replyTo));
    }

    protected void sendMessage(String caseId, String payload, String name, ActorRef<EmitCase> replyTo) {
        boolean isDone = false;
        ActorRef<ParseRow> parser = getGreeterByCaseId(caseId);
        parser.tell(new ParseRow(caseId, payload, isDone, name, replyTo));
    }
}

// --------------------

class CaseIdCursor {
    String lastCaseId;
}
