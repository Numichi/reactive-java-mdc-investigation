package com.example.reactivejavamdc.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import reactor.core.publisher.Mono;

public class WebExchangeFilter implements ExchangeFilterFunction {
    private static final Logger logger = LoggerFactory.getLogger(WebExchangeFilter.class);
    
    @Override
    public Mono<ClientResponse> filter(ClientRequest request, ExchangeFunction next) {
        return Mono.just(request)
            .doOnNext((x) -> logger.info("req - " + Thread.currentThread().getName()))
            .flatMap(next::exchange)
            .doOnNext((x) -> logger.info("res - " + Thread.currentThread().getName()));
    }
}
