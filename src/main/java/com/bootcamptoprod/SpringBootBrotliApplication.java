package com.bootcamptoprod;

import com.aayushatharva.brotli4j.Brotli4jLoader;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootBrotliApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootBrotliApplication.class, args);
    }

    @Override
    public void run(String... args) {
        // Load the native library
        Brotli4jLoader.ensureAvailability();
    }
}
