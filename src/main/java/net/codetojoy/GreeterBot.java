package net.codetojoy;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.*;

import org.apache.commons.io.FileUtils;
import java.io.*;

import net.codetojoy.message.*;

public class GreeterBot extends AbstractBehavior<EmitCase> {
    private File csvFile = null;

    private static final String UTF_8 = "UTF-8";
    private static final boolean DO_APPEND = true;

    public static Behavior<EmitCase> create(String csvFilename) {
        return Behaviors.setup(context -> new GreeterBot(context, csvFilename));
    }

    private GreeterBot(ActorContext<EmitCase> context, String csvFilename) {
        super(context);
        csvFile = new File(csvFilename);
    }

    @Override
    public Receive<EmitCase> createReceive() {
        return newReceiveBuilder().onMessage(EmitCase.class, this::onEmitCase).build();
    }

    private void writeMessageToFile(EmitCase message) {
        String str = message.caseInfoStr + System.lineSeparator();
        try {
            FileUtils.writeStringToFile(csvFile, str, UTF_8, DO_APPEND);
        } catch (IOException ex) {
            System.err.println("TRACER caught exception ! ex: " + ex.getMessage());
        }
    }

    private Behavior<EmitCase> onEmitCase(EmitCase message) {
        getContext().getLog().info("TRACER GreeterBot {}", message.caseInfoStr);
        writeMessageToFile(message);
        return this;
    }
}
