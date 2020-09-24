package com.example;

import akka.actor.typed.ActorSystem;
import java.io.IOException;
import java.util.List;
import java.nio.file.*;

import com.example.message.*;

public class Runner {
    public static void main(String[] args) {
        String csvFilename = args[0];
        ActorSystem<SayHello> greeterMain = ActorSystem.create(GreeterMain.create(csvFilename), "helloakka");
        greeterMain.tell(new SayHello("Charles"));

        try {
            // watchFileUntilQuiet();
            // System.out.println("TRACER sleeping");
            // Thread.sleep(2 * 1000);
            System.out.println(">>> Press ENTER to exit <<<");
            System.in.read();
        } catch (Exception ignored) {
        } finally {
            greeterMain.terminate();
        }
    }

    // experimental
    static void watchFileUntilQuiet() throws Exception {
        WatchService watchService = FileSystems.getDefault().newWatchService();
        Path path = Paths.get("/Users/measter/src/github/codetojoy/COVID-19_CSV_Mapper_Akka");
        path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_MODIFY);
        boolean poll = true;
        while (poll) {
            WatchKey key = watchService.take();
            for (WatchEvent<?> event : key.pollEvents()) {
                System.out.println("TRACER Event kind : " + event.kind() + " - File : " + event.context());
            }
            poll = key.reset();
        }
    }
}
