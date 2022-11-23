package com.dailycodebuffer.ServiceA;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.dailycodebuffer.ServiceA.controller.exception.MyException;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;

@SpringBootApplication
public class ServiceAApplication {
	
	@Autowired
	CircuitBreakerRegistry registory;

	public static void main(String[] args) {
		SpringApplication.run(ServiceAApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	@Bean
	public CircuitBreaker getCircuitBreakerBean() {
		CircuitBreaker cb = null;
		if(registory != null) {
			cb = registory.circuitBreaker("serviceA");
		}
		
		return cb;
	}
}
