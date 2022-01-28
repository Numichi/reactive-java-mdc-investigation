package com.example.reactivejavamdc.controller;

import com.example.reactivejavamdc.filter.WebExchangeFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
public class MdcController {
    
    private static final Logger logger = LoggerFactory.getLogger(MdcController.class);
    
    @GetMapping("counter")
    public Mono<Integer> counter() {
        return Mono.just(1);
    }
    
    @GetMapping("work/{delay}")
    public Mono<Void> work(@PathVariable long delay) {
        return Mono.just(1)
            .delayElement(Duration.ofSeconds(delay))
            .doOnNext(x -> { logger.info("work/" + delay); })
            .then();
        
    }
    
    @GetMapping("notwork/{delay}")
    public Mono<Void> notwork2(@PathVariable long delay) {
        MDC.put("xx", "xx");
        logger.info("xx - " + Thread.currentThread().getName());
        
        return WebClient.builder()
            .filter(new WebExchangeFilter())
            .build()
            .get().uri("http://localhost:3000/?delay=" + delay)
            .retrieve()
            .toBodilessEntity()
            .doOnNext(x -> { test(); })
            .contextWrite(context -> context.put("a", "b"))
            .then();
    }
    
    private void test() {
        MDC.put("yy", "yy");
        logger.info("test - " + Thread.currentThread().getName());
    }
}
