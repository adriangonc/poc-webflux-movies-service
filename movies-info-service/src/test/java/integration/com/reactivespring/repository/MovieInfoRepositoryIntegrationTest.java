package com.reactivespring.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;

@DataMongoTest
@ActiveProfiles("test")
class MovieInfoRepositoryIntegrationTest {

    @Autowired
    MovieInfoRepository movieInfoRepository;

}