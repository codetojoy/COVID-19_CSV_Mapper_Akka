package net.codetojoy;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.*;

import net.codetojoy.message.*;

public class Greeter extends AbstractBehavior<Greet> {
    private CaseInfo caseInfo = new CaseInfo();

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

        if (command.isDone) {
            getContext().getLog().info("TRACER Greeter DONE case: {}", caseInfo.toString());
            String caseInfoStr = caseInfo.toString();
            command.replyTo.tell(new Greeted(caseInfoStr, command.whom, getContext().getSelf()));
        } else {
            if (caseInfo.caseId == null) { caseInfo.caseId = command.caseId; }
            CaseInfo partialCaseInfo = CaseInfos.buildPartialCaseInfo(caseInfo.caseId, command.payload);
            caseInfo.merge(partialCaseInfo);

            getContext().getLog().info("TRACER Greeter case: {}", caseInfo.toString());
        }

        return this;
    }
}
