package com.reactivespring.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class MovieInfoDTO {

    private String id;

    private String name;

    private Integer year;

    private List<String> cast;

    private LocalDate releaseDate;

}
