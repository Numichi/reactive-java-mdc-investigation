package com.example.reactivejavamdc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ReactiveJavaMdcApplication {
    public static void main(String[] args) {
        System.setProperty("reactor.netty.ioWorkerCount", "1");
        SpringApplication.run(ReactiveJavaMdcApplication.class, args);
    }
}
