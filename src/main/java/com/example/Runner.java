package com.example;

import akka.actor.typed.ActorSystem;
import java.io.IOException;
import java.util.List;

import com.example.message.*;

public class Runner {
    public static void main(String[] args) {
        ActorSystem<SayHello> greeterMain = ActorSystem.create(GreeterMain.create(), "helloakka");
        greeterMain.tell(new SayHello("Charles"));

        try {
            System.out.println(">>> Press ENTER to exit <<<");
            System.in.read();
        } catch (IOException ignored) {
        } finally {
            greeterMain.terminate();
        }
    }
}
