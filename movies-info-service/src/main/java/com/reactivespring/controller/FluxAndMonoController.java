package com.reactivespring.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class FluxAndMonoController {

    @GetMapping
    public Flux<Integer> numbersFlux(){
        return Flux.just(1532, 1533, 1534);
    }

}
i