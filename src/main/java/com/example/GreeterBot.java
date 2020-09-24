package com.example;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.*;

import org.apache.commons.io.FileUtils;
import java.io.*;

import com.example.message.*;

public class GreeterBot extends AbstractBehavior<Greeted> {

    public static Behavior<Greeted> create(int max) {
        return Behaviors.setup(context -> new GreeterBot(context, max));
    }

    private final int max;
    private int greetingCounter;

    private GreeterBot(ActorContext<Greeted> context, int max) {
        super(context);
        this.max = max;
    }

    @Override
    public Receive<Greeted> createReceive() {
        return newReceiveBuilder().onMessage(Greeted.class, this::onGreeted).build();
    }

    private static final String UTF_8 = "UTF-8";
    private static final boolean DO_APPEND = true;
    private static final String CSV_FILENAME = "out.csv";
    private static final File CSV_FILE = new File(CSV_FILENAME);

    private void writeMessageToFile(Greeted message) {
        String str = message.caseInfoStr + System.lineSeparator();
        try {
            FileUtils.writeStringToFile(CSV_FILE, str, UTF_8, DO_APPEND);
        } catch (IOException ex) {
            // TODO: ?
        }
    }

    private Behavior<Greeted> onGreeted(Greeted message) {
        greetingCounter++;
        // getContext().getLog().info("Greeting {} for {}", greetingCounter, message.whom);
        if (greetingCounter == max) {
            getContext().getLog().info("TRACER GreeterBot reached max !");

            return Behaviors.stopped();
        } else {
            getContext().getLog().info("TRACER GreeterBot {}", message.caseInfoStr);
            writeMessageToFile(message);
            // message.from.tell(new Greet(message.whom, getContext().getSelf()));
            return this;
        }
    }
}
