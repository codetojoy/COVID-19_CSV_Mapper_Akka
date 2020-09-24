package net.codetojoy.message;

import akka.actor.typed.ActorRef;

import java.util.Objects;

public class Greeted {
    public final String caseInfoStr;
    public final String whom;
    public final ActorRef<Greet> from;

    public Greeted(String caseInfoStr, String whom, ActorRef<Greet> from) {
      this.caseInfoStr = caseInfoStr;
      this.whom = whom;
      this.from = from;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Greeted greeted = (Greeted) o;
        return Objects.equals(caseInfoStr, greeted.caseInfoStr) &&
               Objects.equals(whom, greeted.whom) &&
               Objects.equals(from, greeted.from);
    }

    @Override
    public int hashCode() {
        return Objects.hash(caseInfoStr, whom, from);
    }

    @Override
    public String toString() {
      return "Greeted{" +
              "whom='" + whom + '\'' +
              ", from=" + from +
              '}';
    }
}
