package com.reactivespring.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.List;

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
    void numbersFluxResponseUsingLambdaTest() {
        //given
        List<Integer> numberList = new ArrayList<>();
        numberList.add(1532);
        numberList.add(1533);
        numberList.add(1534);

        webTestClient.get()
                .uri("/number-flux")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBodyList(Integer.class)
                .consumeWith(listEntityExchangeResult -> {

                    var response = listEntityExchangeResult.getResponseBody();
                    assert response.size() == 3;
                    assert (response.equals(numberList));

                });
        //when

        //then

    }

    @Test
    void nameMonoTest() {
        //given

        //when

        //then
        webTestClient.get()
                .uri("/mono-name")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBodyList(String.class)
                .consumeWith(listEntityExchangeResult -> {
                   var response = listEntityExchangeResult.getResponseBody().get(0);
                   assertEquals(response, "Adriano");
                });
    }

    @Test
    void numbersStream() {
        //given

        //when

        //then
    }
}