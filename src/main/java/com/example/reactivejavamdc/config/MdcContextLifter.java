package com.example.reactivejavamdc.config;

import org.reactivestreams.Subscription;
import org.slf4j.MDC;
import reactor.core.CoreSubscriber;
import reactor.util.context.Context;

import java.util.stream.Collectors;

public class MdcContextLifter<T> implements CoreSubscriber<T> {
    private final CoreSubscriber<T> coreSubscriber;
    
    public MdcContextLifter(CoreSubscriber<T> coreSubscriber) {
        this.coreSubscriber = coreSubscriber;
    }
    
    public void onSubscribe(Subscription subscription) {
        copyToMdc(coreSubscriber.currentContext());
        coreSubscriber.onSubscribe(subscription);
    }
    
    public void onNext(T obj) {
        copyToMdc(coreSubscriber.currentContext());
        coreSubscriber.onNext(obj);
    }
    
    public void onError(Throwable t) {
        copyToMdc(coreSubscriber.currentContext());
        coreSubscriber.onError(t);
    }
    
    public void onComplete() {
        copyToMdc(coreSubscriber.currentContext());
        coreSubscriber.onComplete();
    }
    
    @Override
    public Context currentContext() {
        return coreSubscriber.currentContext();
    }
    
    private static int counter = 0;
    
    private void copyToMdc(Context context) {
        counter++;
        System.out.println(counter);
        
        if (!context.isEmpty()) {
            MDC.setContextMap(
                context.stream()
                    .collect(Collectors.toMap(
                            entry -> entry.getKey().toString(),
                            entry -> entry.getValue().toString()
                        )
                    )
            );
        } else {
            MDC.clear();
        }
    }
}