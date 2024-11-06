package com.hsbc.challenge;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.fasterxml.jackson.jakarta.rs.json.JacksonJsonProvider;
import com.hsbc.challenge.controller.BookingResource;
import com.hsbc.challenge.repository.BookingRepository;
import com.hsbc.challenge.service.BookingService;

import io.muserver.MuServer;
import io.muserver.MuServerBuilder;
import io.muserver.rest.RestHandlerBuilder;

@SpringBootApplication
public class ChallengeApplication implements ApplicationRunner {
	private static final Logger logger = LoggerFactory.getLogger(ChallengeApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ChallengeApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		BookingResource userResource = new BookingResource(new BookingService(new BookingRepository()));
		JacksonJsonProvider jacksonJsonProvider = new JacksonJsonProvider();
		MuServer server = MuServerBuilder.httpServer()
				.addHandler(RestHandlerBuilder.restHandler(userResource).addCustomWriter(jacksonJsonProvider)
						.addCustomReader(jacksonJsonProvider).withOpenApiHtmlUrl("/api.html")
						.withOpenApiJsonUrl("/openapi.json"))
				.start();

		logger.info("API HTML: " + server.uri().resolve("/api.html"));
		logger.info("API JSON: " + server.uri().resolve("/openapi.json"));
	}

}
