package com.npichuzhkin.ObjectMapperTask;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ObjectMapperTaskApplication {

	public static void main(String[] args) {
		SpringApplication.run(ObjectMapperTaskApplication.class, args);
	}

	@Bean
	public ObjectMapper objectMapper(){
		return new ObjectMapper();
	}

}
