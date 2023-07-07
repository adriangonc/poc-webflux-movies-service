package com.reactivespring.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

@WebFluxTest(controllers = FluxAndMonoController.class)
@AutoConfigureWebTestClient
class FluxAndMonoControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @Test
    void numbersFluxTest() {
        //given

        //when

        //then
        webTestClient.get()
                .uri("/number-flux")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBodyList(Integer.class)
                .hasSize(3);
    }

    @Test
    void numbersFluxResponseTest() {
        //given
        var numberFlux = webTestClient.get()
                .uri("/number-flux")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .returnResult(Integer.class)
                .getResponseBody();
        //when

        //then
        StepVerifier.create(numberFlux)
                .expectNext(1532, 1533, 1534)
                .verifyComplete();
    }

    @Test
    void numbersStream() {
        //given

        //when

        //then
    }
}