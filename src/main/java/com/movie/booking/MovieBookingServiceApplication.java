package com.movie.booking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class MovieBookingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MovieBookingServiceApplication.class, args);
	}

}