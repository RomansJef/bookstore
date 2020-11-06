package com.bookstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Bookstore App.
 */
@SpringBootApplication
@EnableSwagger2
public class BookstoreApp {
    public static void main(String[] args) {
        SpringApplication.run(BookstoreApp.class, args);
    }
}
