package com.reactivespring.service;

import com.reactivespring.domain.MovieInfo;
import com.reactivespring.repository.MovieInfoRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MovieInfoService {

    public MovieInfoService(MovieInfoRepository movieInfoRepository) {
        this.movieInfoRepository = movieInfoRepository;
    }

    private MovieInfoRepository movieInfoRepository;
    public Mono<MovieInfo> addMovieInfo(MovieInfo movieInfo) {
       return movieInfoRepository.save(movieInfo);
    }

    public Flux<MovieInfo> listAllMoviesInfo() {
        return movieInfoRepository.findAll();
    }

    public Mono<MovieInfo> editMovieInfo(MovieInfo movieInfoEdited, String id) {
        return movieInfoRepository.findById(id).flatMap(movieInfo -> {
            movieInfo.setName(movieInfoEdited.getName());
            movieInfo.setYear(movieInfoEdited.getYear());
            movieInfo.setCast(movieInfoEdited.getCast());
            movieInfo.setReleaseDate(movieInfoEdited.getReleaseDate());
            return movieInfoRepository.save(movieInfo);
        });

    }

    public Mono<MovieInfo> listMovieInfoById(String id) {
        return movieInfoRepository.findById(id);
    }

    public Mono<Void> deleteMovieInfo(String id) {
        return movieInfoRepository.deleteById(id).log();
    }
}
