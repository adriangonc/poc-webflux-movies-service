package com.reactivespring.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.awt.*;
import java.time.Duration;

@RestController
public class FluxAndMonoController {

    @GetMapping("/number-flux")
    public Flux<Integer> numbersFlux(){
        return Flux.just(1532, 1533, 1534)
                .log();
    }

    @GetMapping(value = "/number-stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Long> numbersStream(){
        return Flux.interval(Duration.ofMillis(100))
                .log();
    }

}
