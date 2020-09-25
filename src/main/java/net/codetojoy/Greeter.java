package net.codetojoy;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.*;

import net.codetojoy.message.*;

public class Greeter extends AbstractBehavior<ParseRow> {
    private CaseInfo caseInfo = new CaseInfo();

    public static Behavior<ParseRow> create() {
        return Behaviors.setup(Greeter::new);
    }

    private Greeter(ActorContext<ParseRow> context) {
        super(context);
    }

    @Override
    public Receive<ParseRow> createReceive() {
        return newReceiveBuilder().onMessage(ParseRow.class, this::onParseRow).build();
    }

    private Behavior<ParseRow> onParseRow(ParseRow command) {

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
