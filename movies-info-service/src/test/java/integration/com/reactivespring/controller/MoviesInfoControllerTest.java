package com.reactivespring.controller;

import com.reactivespring.domain.MovieInfo;
import com.reactivespring.repository.MovieInfoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWebTestClient
class MoviesInfoControllerIntegrationTest {

    public static final String MOVIES_INFO_URL = "/v1/movieinfo";
    @Autowired
    MovieInfoRepository movieInfoRepository;

    @Autowired
    WebTestClient webTestClient;

    @BeforeEach
    void setUp(){
        var movieInfos = List.of(new MovieInfo("MVI_1", "Alien", 1979,
                        List.of("Tom Skerritt" ,"Sigourney Weaver" ,"Veronica Cartwright" ,"Harry Dean Stanton"
                                ,"John Hurt" ,"Ian Holm" ,"Yaphet Kotto"),
                        LocalDate.parse("1979-05-25")),
                new MovieInfo("MVI_2", "Túmulo dos Vaga-Lumes", 1988,
                        List.of("Tsutomu Tatsumi" ,"Ayano Shiraishi" ,"Yoshiko Shinohara" ,"Akemi Yamaguchi"),
                        LocalDate.parse("1988-04-16")),
                new MovieInfo("MVI_3", "Mad Max: Estrada da Fúria", 2015,
                        List.of("Tom Hardy" ,"Charlize Theron" ,"Nicholas Hoult"),
                        LocalDate.parse("2015-05-14")));

        movieInfoRepository.saveAll(movieInfos).blockLast();
    }

    @AfterEach
    void tearDown(){
        movieInfoRepository.deleteAll().block();
    }

    @Test
    void addMovieInfo() {
        var movieInfo = new MovieInfo(null, "Barbie", 2023,
                List.of("Margot Robbie" ,"Ryan Gosling"),
                LocalDate.parse("2023-07-20"));

        //when

        webTestClient
                .post()
                .uri(MOVIES_INFO_URL)
                .bodyValue(movieInfo)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody(MovieInfo.class)
                .consumeWith(movieInfoEntityExchangeResult -> {
                    var createdMovieInfo = movieInfoEntityExchangeResult.getResponseBody();
                    assert createdMovieInfo != null;
                    assert createdMovieInfo.getMovieInfoId()!=null;
                });

        //then


    }

    @Test
    void listAllMoviesInfoTest() {
        //when

        webTestClient
                .get()
                .uri(MOVIES_INFO_URL)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBodyList(MovieInfo.class)
                .hasSize(3);

        //then


    }

    @Test
    void getMovieInfoByIdTest() {
        //when
        var movieInfoId = "MVI_1";

        webTestClient
                .get()
                .uri(MOVIES_INFO_URL+"/{id}", movieInfoId)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody()
                .jsonPath("$.name").isEqualTo("Alien");

        //then


    }

}