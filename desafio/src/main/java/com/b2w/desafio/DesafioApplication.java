package com.b2w.desafio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class DesafioApplication implements CommandLineRunner {

	@Autowired
	private SwApiService swApiService;
	
	public static void main(String[] args) {
		SpringApplication.run(DesafioApplication.class, args);
	}
	
	@Bean
	public RestTemplate getRestTemplate() {
	      return new RestTemplate(); 
	}
	
	@Override
	public void run(String... args) throws Exception {
		swApiService.loadData();
	}
}
