package com.ausy.backend;

import com.ausy.backend.Exceptions.ErrorResponse;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackendApplication {

    public static void main(String[] args) {
        ErrorResponse.setupLogger();
        SpringApplication.run(BackendApplication.class, args);
    }

}
