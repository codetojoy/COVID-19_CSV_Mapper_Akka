package com.example.message;

import akka.actor.typed.ActorRef;

public final class Greet {
    public final String caseId;
    public final String payload;
    public final boolean isDone;
    public final String whom;
    public final ActorRef<Greeted> replyTo;

    public Greet(String caseId, String payload, boolean isDone, String whom, ActorRef<Greeted> replyTo) {
        this.caseId = caseId;
        this.payload = payload;
        this.isDone = isDone;
        this.whom = whom;
        this.replyTo = replyTo;
    }
}
