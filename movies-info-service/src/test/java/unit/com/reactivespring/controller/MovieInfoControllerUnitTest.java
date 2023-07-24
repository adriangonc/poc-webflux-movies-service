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
import reactor.test.StepVerifier;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.isA;
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

    @Test
    void saveMovieInfo(){
        //given

        //when
        var moviesInfoMono = new MovieInfo(null, "Parasita", 2019,
                List.of("Song Kang-ho" ,"Jang Hye-jin" ,"Choi Woo-shik" ,"Park So-dam"
                        ,"Lee Sun-kyun" ,"Cho Yeo-jeong"), LocalDate.parse("2019-07-11"));

        when(movieInfoServiceMock.addMovieInfo(isA(MovieInfo.class))).thenReturn(
                Mono.just(new MovieInfo("MOCK_ID1", "Parasita", 2019,
                        List.of("Song Kang-ho" ,"Jang Hye-jin" ,"Choi Woo-shik" ,"Park So-dam"
                                ,"Lee Sun-kyun" ,"Cho Yeo-jeong"), LocalDate.parse("2019-07-11")))
        );

        //then
        webTestClient
                .post()
                .uri(Utils.MOVIES_INFO_URL)
                .bodyValue(moviesInfoMono)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody(MovieInfo.class)
                .consumeWith(movieInfoEntityExchangeResult -> {
                    var savedMovieInfo = movieInfoEntityExchangeResult.getResponseBody();
                    assert savedMovieInfo != null;
                    assert savedMovieInfo.getMovieInfoId() != null;
                    assertEquals("MOCK_ID1", savedMovieInfo.getMovieInfoId());
                });
    }

    @Test
    void editMovieInfo() {
        var movieInfo = new MovieInfo(null, "Parasita", 2019,
                List.of("Song Kang-ho" ,"Jang Hye-jin" ,"Choi Woo-shik" ,"Park So-dam"
                        ,"Lee Sun-kyun" ,"Cho Yeo-jeong"), LocalDate.parse("2019-07-11"));

        var movieInfoId = "MVI_3";

        when(movieInfoServiceMock.editMovieInfo(isA(MovieInfo.class), isA(String.class))).thenReturn(
                Mono.just(new MovieInfo(movieInfoId, "Parasita", 2019,
                        List.of("Song Kang-ho" ,"Jang Hye-jin" ,"Choi Woo-shik" ,"Park So-dam"
                                ,"Lee Sun-kyun" ,"Cho Yeo-jeong"), LocalDate.parse("2019-07-11")))
        );

        //when

        webTestClient
                .put()
                .uri(Utils.MOVIES_INFO_URL+"/{id}", movieInfoId)
                .bodyValue(movieInfo)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody(MovieInfo.class)
                .consumeWith(movieInfoEntityExchangeResult -> {
                    var updatedMovieInfo = movieInfoEntityExchangeResult.getResponseBody();
                    assert updatedMovieInfo != null;
                    assert updatedMovieInfo.getMovieInfoId()!=null;

                    assertEquals("Parasita", updatedMovieInfo.getName());
                });

        //then

    }

    @Test
    void deleteMovieInfoTest(){
        var movieInfoId = "MVI_3";

        when(movieInfoServiceMock.deleteMovieInfo(isA(String.class))).thenReturn(
                Mono.empty()
        );

        webTestClient.delete()
                .uri(Utils.MOVIES_INFO_URL+"/{id}",movieInfoId)
                .exchange()
                .expectStatus()
                .isNoContent()
                .expectBody(Void.class);


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

    @Test
    void addMovieInfoValidation(){
        //given

        //when
        var moviesInfoMono = new MovieInfo(null, "", -2019,
                List.of(""), LocalDate.parse("2019-07-11"));

        //then
        webTestClient
                .post()
                .uri(Utils.MOVIES_INFO_URL)
                .bodyValue(moviesInfoMono)
                .exchange()
                .expectStatus()
                .isBadRequest()
                .expectBody(String.class)
                .consumeWith(stringEntityExchangeResult -> {
                    var response = stringEntityExchangeResult.getResponseBody();
                    System.out.println("responseBody: " + response);
                    var expectedErrorMsg = "movieInfo.cast must be present!,movieInfo.name must be present!,movieInfo.year must be a positive number!";
                    assert response != null;
                    assertEquals(expectedErrorMsg, response);
                });
    }
}
