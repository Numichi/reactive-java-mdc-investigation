package com.example.reactivejavamdc.filter;

import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
public class Filter implements WebFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        return chain.filter(exchange).contextWrite(ctx -> {
            try {
                var data = exchange.getRequest().getQueryParams().get("data").get(0);
                return ctx.putNonNull("MDC", data);
            } catch (Exception exception) {
                return ctx;
            }
        });
    }
}
