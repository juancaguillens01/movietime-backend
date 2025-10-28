package com.movietime.user_service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class UserServiceApplicationTests {

    @Test
    void contextLoads() {
        // This test ensures that the Spring application context loads correctly.
        // An empty method is sufficient; if the context fails to load,
        // the application will throw an exception before the test finishes.
    }
}
