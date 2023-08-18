package com.reactivespring.routes;

import com.reactivespring.domain.Review;
import com.reactivespring.repository.ReviewRepository;
import com.reactivespring.utils.ReviewConst;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWebTestClient
public class ReviewsIntegrationTest {



    @Autowired
    WebTestClient webTestClient;

    @Autowired
    ReviewRepository reviewRepository;

    @BeforeEach
    void setUp(){
        var reviewList = List.of(
                new Review(null, 1L, "Greate movie", 9.7),
                new Review(null, 2L, "Test review", 8.0),
                new Review(null, 3L, "Test review adriano", 10.0)
        );

        reviewRepository.saveAll(reviewList).blockLast();
    }

    @AfterEach
    void tearDown(){
        reviewRepository.deleteAll().block();
    }

    @Test
    void addReviewTest(){
        //given
        var review = new Review(null, 1L, "Greate movie add review test", 9.7);

        //when
        webTestClient.post().uri(ReviewConst.REVIEWS_URL_V1)
                .bodyValue(review)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody(Review.class)
                .consumeWith( reviewEntityExchangeResult -> {
                    var savedReview = reviewEntityExchangeResult.getResponseBody();
                    assert savedReview != null;
                    assert savedReview.getReviewId() != null;
                    System.out.println(savedReview);
                });

    }

}
