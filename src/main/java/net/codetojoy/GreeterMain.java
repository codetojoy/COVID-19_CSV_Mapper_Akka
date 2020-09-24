package net.codetojoy;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.*;

import java.util.*;
import java.util.stream.*;

import net.codetojoy.data.*;
import net.codetojoy.message.*;

public class GreeterMain extends AbstractBehavior<SayHello> {

    private static final DataSource dataSource = new SimpleDataSource();
    private static String csvFilename;

    private Map<String,ActorRef<Greet>> greeterMap = new HashMap<>();

    public static Behavior<SayHello> create(String csvFilename) {
        GreeterMain.csvFilename = csvFilename;
        return Behaviors.setup(GreeterMain::new);
    }

    private GreeterMain(ActorContext<SayHello> context) {
        super(context);
    }

    @Override
    public Receive<SayHello> createReceive() {
        return newReceiveBuilder().onMessage(SayHello.class, this::onSayHello).build();
    }

    private Behavior<SayHello> onSayHello(SayHello command) {
        ActorRef<Greeted> replyTo = getContext().spawn(GreeterBot.create(csvFilename), command.name);

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
                                ActorRef<Greeted> replyTo) {
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

    protected ActorRef<Greet> getGreeterByCaseId(String caseId) {
        ActorRef<Greet> greeter = null;

        if (greeterMap.keySet().contains(caseId)) {
            greeter = greeterMap.get(caseId);
        } else {
            greeter = getContext().spawn(Greeter.create(), Constants.ACTOR_NAME_PREFIX + caseId);
            greeterMap.put(caseId, greeter);
        }

        return greeter;
    }

    protected void sendDoneMessage(String caseId, String name, ActorRef<Greeted> replyTo) {
        String payload = "";
        boolean isDone = true;
        ActorRef<Greet> greeter = getGreeterByCaseId(caseId);
        greeter.tell(new Greet(caseId, payload, isDone, name, replyTo));
    }

    protected void sendMessage(String caseId, String payload, String name, ActorRef<Greeted> replyTo) {
        boolean isDone = false;
        ActorRef<Greet> greeter = getGreeterByCaseId(caseId);
        greeter.tell(new Greet(caseId, payload, isDone, name, replyTo));
    }
}
