package com.example.reactivejavamdc.config;

import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Hooks;
import reactor.core.publisher.Operators;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;


@Configuration
public class MdcContextLifterConfiguration {
    public static String MDC_CONTEXT_REACTOR_KEY = MdcContextLifterConfiguration.class.getName();
    
    @PostConstruct
    private void contextOperatorHook() {
            Hooks.onEachOperator(MDC_CONTEXT_REACTOR_KEY,
                Operators.lift((sc, sub) -> new MdcContextLifter(sub)));
    }
    
    @PreDestroy
    private void cleanupHook() {
        Hooks.resetOnEachOperator(MDC_CONTEXT_REACTOR_KEY);
    }
}
