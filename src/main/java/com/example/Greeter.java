package com.example;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.*;

import java.util.concurrent.atomic.AtomicInteger;

import com.example.message.*;

public class Greeter extends AbstractBehavior<Greet> {
  private CaseInfo caseInfo = new CaseInfo();
  private AtomicInteger messageCounter = new AtomicInteger(0);

  public static Behavior<Greet> create() {
    return Behaviors.setup(Greeter::new);
  }

  private Greeter(ActorContext<Greet> context) {
    super(context);
  }

  @Override
  public Receive<Greet> createReceive() {
    return newReceiveBuilder().onMessage(Greet.class, this::onGreet).build();
  }

  private Behavior<Greet> onGreet(Greet command) {
    int value = messageCounter.incrementAndGet();

    if (command.isDone) {
        getContext().getLog().info("TRACER Greeter DONE msg: {} case: {}", value, caseInfo.toString());
        String caseInfoStr = caseInfo.toString();
        command.replyTo.tell(new Greeted(caseInfoStr, command.whom, getContext().getSelf()));
    } else {
        if (caseInfo.caseId == null) { caseInfo.caseId = command.caseId; }

        String[] payloadTokens = command.payload.split(Constants.TOKEN_SEPARATOR);
        int index = 0;
        for (String payloadToken : payloadTokens) {
            if ((payloadToken != null) && (! payloadToken.isEmpty())) {
                if (index == 0) { caseInfo.a = payloadToken; }
                if (index == 1) { caseInfo.b = payloadToken; }
                if (index == 2) { caseInfo.c = payloadToken; }
                if (index == 3) { caseInfo.d = payloadToken; }
            }
            index++;
        }

        getContext().getLog().info("TRACER Greeter msg: {} case: {}", value, caseInfo.toString());
    }

    return this;
  }
}
