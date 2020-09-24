package com.example;

import akka.actor.typed.ActorSystem;
import java.io.*;
import java.util.List;

import net.codetojoy.message.*;

public class Runner {
    private static String csvFilename;
    private static  final int DELAY_IN_MILLIS = 1000;

    public static void main(String[] args) {
        csvFilename = args[0];
        ActorSystem<SayHello> greeterMain = ActorSystem.create(GreeterMain.create(csvFilename), "helloakka");
        greeterMain.tell(new SayHello("Charles"));

        try {
            watchCsvFileUntilQuiet();
        } catch (Exception ignored) {
        } finally {
            greeterMain.terminate();
        }
    }

    static void watchCsvFileUntilQuiet() throws Exception {
        File csvFile = new File(csvFilename);
        long lastTimeStamp = csvFile.lastModified();

        boolean isDone = false;

        while (! isDone) {
            try { Thread.sleep(DELAY_IN_MILLIS); } catch (Exception ex) {}
            long timeStamp = csvFile.lastModified();

            if (timeStamp == lastTimeStamp) {
                isDone = true;
            } else {
                System.out.println("TRACER csv file still being modified");
                lastTimeStamp = timeStamp;
            }
        }
    }
}
