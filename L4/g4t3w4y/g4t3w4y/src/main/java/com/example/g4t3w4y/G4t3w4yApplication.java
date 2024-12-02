package com.example.g4t3w4y;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class G4t3w4yApplication {

	public static void main(String[] args) {
		SpringApplication.run(G4t3w4yApplication.class, args);
	}

	@Bean
	public RouteLocator routeLocator(RouteLocatorBuilder builder) {
		return builder.routes()
				// Route for libraries
				.route("libraries_route", r -> r
						.path("/api/libraries/**") // Matches /api/libraries and all subpaths
						.and()
						.method("GET", "POST", "PUT", "DELETE")
						.uri("http://localhost:5002")) // Target microservice

				// Route for books
				.route("books_route", r -> r
						.path("/api/books/**") // Matches /api/books and all subpaths
						.and()
						.method("GET", "POST", "PUT", "DELETE")
						.uri("http://localhost:5001")) // Target microservice

				.build();
	}
}
