package net.codetojoy;

import akka.actor.typed.ActorSystem;
import java.io.*;
import java.util.List;

import net.codetojoy.message.*;

public class Runner {
    private static String inputCsvFilename;
    private static String outputCsvFilename;
    private static  final int DELAY_IN_MILLIS = 2000;

    public static void main(String[] args) {
        inputCsvFilename = args[0];
        outputCsvFilename = args[1];
        ActorSystem<BeginProcessing> greeterMain = ActorSystem.create(ParserMain.create(inputCsvFilename, outputCsvFilename), "csv_akka");
        greeterMain.tell(new BeginProcessing("csv"));

        try {
            watchCsvFileUntilQuiet();
        } catch (Exception ignored) {
        } finally {
            greeterMain.terminate();
        }
    }

    static void watchCsvFileUntilQuiet() throws Exception {
        File outputCsvFile = new File(outputCsvFilename);
        long lastTimeStamp = outputCsvFile.lastModified();

        boolean isDone = false;

        while (! isDone) {
            try { Thread.sleep(DELAY_IN_MILLIS); } catch (Exception ex) {}
            long timeStamp = outputCsvFile.lastModified();

            if (timeStamp == lastTimeStamp) {
                isDone = true;
            } else {
                System.out.println("TRACER outputCsv file still being modified");
                lastTimeStamp = timeStamp;
            }
        }
    }
}
