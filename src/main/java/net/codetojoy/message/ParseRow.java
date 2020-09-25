package net.codetojoy.message;

import akka.actor.typed.ActorRef;

public final class ParseRow {
    public final String caseId;
    public final String payload;
    public final boolean isDone;
    public final String whom;
    public final ActorRef<EmitCase> replyTo;

    public ParseRow(String caseId, String payload, boolean isDone, String whom, ActorRef<EmitCase> replyTo) {
        this.caseId = caseId;
        this.payload = payload;
        this.isDone = isDone;
        this.whom = whom;
        this.replyTo = replyTo;
    }
}
