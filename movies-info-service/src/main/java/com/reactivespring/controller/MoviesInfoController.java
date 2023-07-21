package com.reactivespring.controller;

import com.reactivespring.domain.MovieInfo;
import com.reactivespring.dto.MovieInfoDTO;
import com.reactivespring.service.MovieInfoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1")
public class MoviesInfoController {

    private MovieInfoService movieInfoService;

    public MoviesInfoController(MovieInfoService movieInfoService) {
        this.movieInfoService = movieInfoService;
    }

    @GetMapping("/movieinfo")
    public Flux<MovieInfo> listAllMoviesInfo(){
        return movieInfoService.listAllMoviesInfo();
    }

    @PostMapping("/movieinfo")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<MovieInfo> addMovieInfo(@RequestBody MovieInfo movieInfo){
        return movieInfoService.addMovieInfo(movieInfo);
    }

    @PutMapping("/movieinfo/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<MovieInfo> editMovieInfo(@RequestBody MovieInfo movieInfoDTO, @PathVariable String id){
        return movieInfoService.editMovieInfo(movieInfoDTO, id);
    }

    @GetMapping("/movieinfo/{id}")
    public Mono<MovieInfo> listMovieInfoById(@PathVariable String id){

        return movieInfoService.listMovieInfoById(id);
    }

}
