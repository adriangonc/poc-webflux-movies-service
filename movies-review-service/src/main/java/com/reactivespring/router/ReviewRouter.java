package com.reactivespring.router;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class ReviewRouter {

    public RouterFunction<ServerResponse> reviewsRoute(){
        return route()
                .GET("/v1/helloworld", (request -> ServerResponse.ok().bodyValue("Olá mundo!"))).build();
    }

}