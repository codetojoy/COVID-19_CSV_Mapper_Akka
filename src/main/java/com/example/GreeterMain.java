package com.example;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.*;

import java.util.*;
import java.util.stream.*;

import com.example.data.*;
import com.example.message.*;

public class GreeterMain extends AbstractBehavior<SayHello> {

    private Map<String,ActorRef<Greet>> greeterMap = new HashMap<>();

    public static Behavior<SayHello> create() {
        return Behaviors.setup(GreeterMain::new);
    }

    private GreeterMain(ActorContext<SayHello> context) {
        super(context);
    }

    @Override
    public Receive<SayHello> createReceive() {
        return newReceiveBuilder().onMessage(SayHello.class, this::onSayHello).build();
    }

    private static final DataSource dataSource = new SimpleDataSource();
    // TODO: this is not good
    private static final int MAX_MESSAGES = dataSource.getMax();

    private Behavior<SayHello> onSayHello(SayHello command) {
        ActorRef<Greeted> replyTo = getContext().spawn(GreeterBot.create(MAX_MESSAGES), command.name);

        Stream<String> dataInfoStream = dataSource.getData();
        List<String> dataInfoStrings = dataInfoStream.collect(Collectors.toList());

        boolean isInitialCase = true;
        String lastCaseId = "";
        for (String dataInfoString : dataInfoStrings) {
            DataInfo dataInfo = dataSource.getDataInfo(dataInfoString);
            String caseId = dataInfo.caseId;

            if (! greeterMap.keySet().contains(caseId)) {
                ActorRef<Greet> greeter = getContext().spawn(Greeter.create(), "greeter" + caseId);
                greeterMap.put(caseId, greeter);
            }

            if (isInitialCase) {
                lastCaseId = caseId;
                isInitialCase = false;
            }

            getContext().getLog().info("TRACER GM {} {} {}", lastCaseId, caseId, dataInfo.payload);

            if (! caseId.equals(lastCaseId)) {
                // new boundary, so done
                sendDoneMessage(lastCaseId, command.name, replyTo);
                lastCaseId = caseId;
            }

            sendMessage(caseId, dataInfo.payload, command.name, replyTo);
        }

        // new boundary, so done
        sendDoneMessage(lastCaseId, command.name, replyTo);

        return this;
    }

    protected void sendDoneMessage(String caseId, String name, ActorRef<Greeted> replyTo) {
        String payload = "";
        boolean isDone = true;
        ActorRef<Greet> greeter = greeterMap.get(caseId);
        greeter.tell(new Greet(caseId, payload, isDone, name, replyTo));
    }

    protected void sendMessage(String caseId, String payload, String name, ActorRef<Greeted> replyTo) {
        boolean isDone = false;
        ActorRef<Greet> greeter = greeterMap.get(caseId);
        greeter.tell(new Greet(caseId, payload, isDone, name, replyTo));
    }
}
