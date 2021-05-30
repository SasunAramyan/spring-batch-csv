package com.example.interview.bach;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.example")
@EnableJpaRepositories(basePackages = "com.example.interview.bach.data.jpa")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
