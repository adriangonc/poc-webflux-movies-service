package com.reactivespring.controller;

import com.reactivespring.domain.MovieInfo;
import com.reactivespring.service.MovieInfoService;
import com.reactivespring.utils.Utils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.when;


@WebFluxTest(controllers = MoviesInfoController.class)
@AutoConfigureWebTestClient
public class MovieInfoControllerUnitTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private MovieInfoService movieInfoServiceMock;

    @Test
    void getAllMoviesInfoTest(){
        //when
        when(movieInfoServiceMock.listAllMoviesInfo()).thenReturn(Flux.fromIterable(createMoviesList()));

        webTestClient
                .get()
                .uri(Utils.MOVIES_INFO_URL)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBodyList(MovieInfo.class)
                .hasSize(3);

        //then
    }

    @Test
    void getMovieInfoByIdTest(){
        //given
        var movieInfoId = "MVI_1";

        //when
        when(movieInfoServiceMock.listMovieInfoById(movieInfoId)).thenReturn(createMovieInfo());

        webTestClient
                .get()
                .uri(Utils.MOVIES_INFO_URL+"/{id}" ,movieInfoId)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody()
                .consumeWith(movieInfoEntityExchangeResult -> {
                    var movieInfo = movieInfoEntityExchangeResult.getResponseBody();
                    assert movieInfo != null;
                })
                .jsonPath("$.name").isEqualTo("Alien");

        //then
    }

    public List<MovieInfo> createMoviesList(){
        return List.of(new MovieInfo("MVI_1", "Alien", 1979,
                        List.of("Tom Skerritt" ,"Sigourney Weaver" ,"Veronica Cartwright" ,"Harry Dean Stanton"
                                ,"John Hurt" ,"Ian Holm" ,"Yaphet Kotto"),
                        LocalDate.parse("1979-05-25")),
                new MovieInfo("MVI_2", "Túmulo dos Vaga-Lumes", 1988,
                        List.of("Tsutomu Tatsumi" ,"Ayano Shiraishi" ,"Yoshiko Shinohara" ,"Akemi Yamaguchi"),
                        LocalDate.parse("1988-04-16")),
                new MovieInfo("MVI_3", "Mad Max: Estrada da Fúria", 2015,
                        List.of("Tom Hardy" ,"Charlize Theron" ,"Nicholas Hoult"),
                        LocalDate.parse("2015-05-14")));
    }

    public Mono<MovieInfo> createMovieInfo(){
        var moveInfo = new MovieInfo("MVI_1", "Alien", 1979,
                List.of("Tom Skerritt" ,"Sigourney Weaver" ,"Veronica Cartwright" ,"Harry Dean Stanton"
                        ,"John Hurt" ,"Ian Holm" ,"Yaphet Kotto"),
                LocalDate.parse("1979-05-25"));
        return Mono.just(moveInfo);
    }
}
