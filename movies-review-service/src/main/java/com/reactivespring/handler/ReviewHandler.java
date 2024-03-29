package com.reactivespring.handler;

import com.reactivespring.domain.Review;
import com.reactivespring.repository.ReviewRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class ReviewHandler {

    private ReviewRepository reviewRepository;

    public ReviewHandler(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public Mono<ServerResponse> addReview(ServerRequest request) {

        return request.bodyToMono(Review.class)
                .flatMap(reviewRepository::save)
                .flatMap(savedReview -> {
                    return ServerResponse.status(HttpStatus.CREATED)
                            .bodyValue(savedReview);
                });

    }

    public Mono<ServerResponse> getReviews(ServerRequest request) {
        var reviewsFlux = reviewRepository.findAll();
        Mono<ServerResponse> body = ServerResponse.ok().body(reviewsFlux, Review.class);
        return body;
    }
}
