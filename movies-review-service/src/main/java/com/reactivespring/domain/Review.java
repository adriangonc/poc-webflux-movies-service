package com.reactivespring.domain;

import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@Document
public class Review {

    @Id
    private String reviewId;
    private Long movieInfoId;
    private String comment;
    //@Min(value = 0L, message = "rating.negative : rating is negative and please pass a non-negative value")
    private Double rating;

    public Review(String reviewId, Long movieInfoId, String comment, Double rating) {
        this.reviewId = reviewId;
        this.movieInfoId = movieInfoId;
        this.comment = comment;
        this.rating = rating;
    }

    public String getReviewId() {
        return reviewId;
    }

    public void setReviewId(String reviewId) {
        this.reviewId = reviewId;
    }

    public Long getMovieInfoId() {
        return movieInfoId;
    }

    public void setMovieInfoId(Long movieInfoId) {
        this.movieInfoId = movieInfoId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Review{" +
                "reviewId='" + reviewId + '\'' +
                ", movieInfoId=" + movieInfoId +
                ", comment='" + comment + '\'' +
                ", rating=" + rating +
                '}';
    }
}
