package com.reactivespring.service;

import com.reactivespring.domain.MovieInfo;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IMovieInfoService {

    public Flux<MovieInfo> listAllMoviesInfo();

    public Mono<MovieInfo> editMovieInfo(MovieInfo movieInfoEdited, String id);

    public Mono<MovieInfo> listMovieInfoById(String id);

    public Mono<Void> deleteMovieInfo(String id);

    public Flux<MovieInfo> getMoviesInfoByYear(Integer year);

    public Mono<MovieInfo> getMovieByName(String name);

    public Mono<MovieInfo> addMovieInfo(MovieInfo movieInfo);

}
