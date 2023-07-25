package com.reactivespring.repository;

import com.reactivespring.domain.MovieInfo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;
import reactor.test.StepVerifier;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataMongoTest
@ActiveProfiles("test")
class MovieInfoRepositoryIntegrationTest {

    @Autowired
    MovieInfoRepository movieInfoRepository;

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
    void findAll(){
        //given

        //when
        var moviesInfoFlux = movieInfoRepository.findAll().log();

        //then
        StepVerifier.create(moviesInfoFlux)
                .expectNextCount(3)
                .verifyComplete();
    }

    @Test
    void findById(){
        //given

        //when
        var moviesInfoMono = movieInfoRepository.findById("MVI_2").log();

        //then
        StepVerifier.create(moviesInfoMono)
                .assertNext(movieInfo -> {
                    assertEquals("Túmulo dos Vaga-Lumes", movieInfo.getName());
                })
                .verifyComplete();
    }

    @Test
    void saveMovieInfo(){
        //given

        //when
        var moviesInfoMono = new MovieInfo("MVI_4", "Parasita", 2019,
                List.of("Song Kang-ho" ,"Jang Hye-jin" ,"Choi Woo-shik" ,"Park So-dam"
                        ,"Lee Sun-kyun" ,"Cho Yeo-jeong"), LocalDate.parse("2019-07-11"));

        var moviesInfoEdited = movieInfoRepository.save(moviesInfoMono).log();

        //then
        StepVerifier.create(moviesInfoEdited)
                .assertNext(movieInfo -> {
                    assertNotNull(movieInfo.getMovieInfoId());
                    assertEquals("Parasita", movieInfo.getName());
                })
                .verifyComplete();
    }

    @Test
    void updateMovieInfo(){
        //given

        //when
        var moviesInfoMono = movieInfoRepository.findById("MVI_1").block();
        moviesInfoMono.setName("Alien: O oitavo passageiro");

        var moviesInfoEdited = movieInfoRepository.save(moviesInfoMono).log();

        //then
        StepVerifier.create(moviesInfoEdited)
                .assertNext(movieInfo -> {
                    assertNotNull(movieInfo.getMovieInfoId());
                    assertEquals("Alien: O oitavo passageiro", movieInfo.getName());
                })
                .verifyComplete();
    }

    @Test
    void deleteMovieInfo(){
        //given

        //when
        movieInfoRepository.deleteById("MVI_1").block();
        var moviesInfoFlux = movieInfoRepository.findAll().log();

        //then
        StepVerifier.create(moviesInfoFlux)
                .expectNextCount(2)
                .verifyComplete();
    }

    @Test
    void findByYearTest(){
        //given
        var year = 1979;

        //when
        var moviesInfoFlux = movieInfoRepository.findByYear(year).log();

        //then
        StepVerifier.create(moviesInfoFlux)
                .expectNextCount(1)
                .verifyComplete();
    }

}