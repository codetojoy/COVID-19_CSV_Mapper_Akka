package net.codetojoy.message;

import akka.actor.typed.ActorRef;

import java.util.Objects;

public class EmitCase {
    public final String caseInfoStr;
    public final String whom;
    public final ActorRef<ParseRow> from;

    public EmitCase(String caseInfoStr, String whom, ActorRef<ParseRow> from) {
      this.caseInfoStr = caseInfoStr;
      this.whom = whom;
      this.from = from;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmitCase emitCase = (EmitCase) o;
        return Objects.equals(caseInfoStr, emitCase.caseInfoStr) &&
               Objects.equals(whom, emitCase.whom) &&
               Objects.equals(from, emitCase.from);
    }

    @Override
    public int hashCode() {
        return Objects.hash(caseInfoStr, whom, from);
    }

    @Override
    public String toString() {
      return "EmitCase{" +
              "whom='" + whom + '\'' +
              ", from=" + from +
              '}';
    }
}
