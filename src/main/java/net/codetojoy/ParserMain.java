package net.codetojoy;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.*;

import java.util.*;
import java.util.stream.*;

import net.codetojoy.data.*;
import net.codetojoy.message.*;
import net.codetojoy.util.Constants;

public class ParserMain extends AbstractBehavior<BeginProcessing> {

    private static DataSource dataSource;
    private static String outputCsvFilename;

    private Map<String,ActorRef<ParseRow>> parserMap = new HashMap<>();

    private Set<String> allCaseIds = new HashSet<>();
    private Set<String> completeCaseIds = new HashSet<>();
    private int logCounter = 0;
    private static final int LOG_FREQUENCY = 2000;

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
            ActorRef<EmitCase> replyTo = getContext().spawn(Emitter.create(outputCsvFilename), command.name);

            Stream<String> dataInfoStream = dataSource.getData();
            List<String> dataInfoStrings = dataInfoStream.collect(Collectors.toList());

            String lastCaseId = null;
            for (String dataInfoString : dataInfoStrings) {
                String commandName = command.name;
                lastCaseId = processDataInfo(dataInfoString, lastCaseId, commandName, replyTo);

                if (logCounter % LOG_FREQUENCY == 0) {
                    getContext().getLog().info("TRACER ParserMain allCaseIds: {} completeCaseIds: {} ",
                                                allCaseIds.size(), completeCaseIds.size());
                }
                if (logCounter > 100_000) {
                    getContext().getLog().info("TRACER ParserMain ITER lastCaseId: {}", lastCaseId);
                }
                logCounter++;
            }

            getContext().getLog().info("TRACER ParserMain cp abc");

            // finish off the last boundary
            sendDoneMessage(lastCaseId, command.name, replyTo);

            getContext().getLog().info("TRACER ParserMain FINAL allCaseIds: {} completeCaseIds: {} ",
                                        allCaseIds.size(), completeCaseIds.size());
        } catch (Exception ex) {
            getContext().getLog().error("TRACER ParserMain caught exception! ex: {}", ex.getMessage());
        }

        return this;
    }

    protected String processDataInfo(String dataInfoString, String lastCaseId, String commandName,
                                ActorRef<EmitCase> replyTo) {
        String result = lastCaseId;
        DataInfo dataInfo = dataSource.getDataInfo(dataInfoString);

        // TODO: filter by region ?
        RowFilter rowFilter = DataSources.getRowFilter();
        boolean doInclude = (! rowFilter.doExclude(dataInfo));

        if (doInclude) {
            String caseId = dataInfo.caseId;

            allCaseIds.add(caseId);

            getContext().getLog().info("TRACER ParserMain lastCaseId: {} caseId: {}", lastCaseId, caseId);

            if (lastCaseId == null) {
                lastCaseId = caseId;
            }

            if (! caseId.equals(lastCaseId)) {
                // new boundary, so done
                sendDoneMessage(lastCaseId, commandName, replyTo);
                lastCaseId = caseId;
                completeCaseIds.add(caseId);
            }

            sendMessage(caseId, dataInfo.payload, commandName, replyTo);

            result = caseId;
        } else {
            getContext().getLog().error("TRACER illegal caseId: {}", dataInfo.caseId);
        }

        return result;
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
