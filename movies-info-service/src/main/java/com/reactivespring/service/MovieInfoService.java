package com.reactivespring.service;

import com.reactivespring.domain.MovieInfo;
import com.reactivespring.dto.MovieInfoDTO;
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

    public Mono<MovieInfo> editMovieInfo(MovieInfo movieInfo) {

        //TODO terminar implementação
        return movieInfoRepository.save(movieInfo);
    }
}
