package net.codetojoy;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.*;

import java.util.*;
import java.util.stream.*;

import net.codetojoy.data.*;
import net.codetojoy.message.*;

public class ParserMain extends AbstractBehavior<BeginProcessing> {

    private static final DataSource dataSource = new SimpleDataSource();
    private static String csvFilename;

    private Map<String,ActorRef<ParseRow>> parserMap = new HashMap<>();

    public static Behavior<BeginProcessing> create(String csvFilename) {
        ParserMain.csvFilename = csvFilename;
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
        ActorRef<EmitCase> replyTo = getContext().spawn(Emitter.create(csvFilename), command.name);

        Stream<String> dataInfoStream = dataSource.getData();
        List<String> dataInfoStrings = dataInfoStream.collect(Collectors.toList());

        String lastCaseId = null;
        for (String dataInfoString : dataInfoStrings) {
            String commandName = command.name;
            lastCaseId = processDataInfo(dataInfoString, lastCaseId, commandName, replyTo);
        }

        // finish off the last boundary
        sendDoneMessage(lastCaseId, command.name, replyTo);

        return this;
    }

    protected String processDataInfo(String dataInfoString, String lastCaseId, String commandName,
                                ActorRef<EmitCase> replyTo) {
        DataInfo dataInfo = dataSource.getDataInfo(dataInfoString);
        String caseId = dataInfo.caseId;

        // TODO: filter by region

        if (lastCaseId == null) {
            lastCaseId = caseId;
        }

        // getContext().getLog().info("TRACER GM {} {} {}", lastCaseId, caseId, dataInfo.payload);

        if (! caseId.equals(lastCaseId)) {
            // new boundary, so done
            sendDoneMessage(lastCaseId, commandName, replyTo);
            lastCaseId = caseId;
        }

        sendMessage(caseId, dataInfo.payload, commandName, replyTo);

        return caseId;
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
