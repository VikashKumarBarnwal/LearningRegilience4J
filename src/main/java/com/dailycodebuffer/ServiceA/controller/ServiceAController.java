package com.dailycodebuffer.ServiceA.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.dailycodebuffer.ServiceA.controller.exception.MyException;
import com.dailycodebuffer.ServiceA.controller.exception.MyException2;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@RestController
@RequestMapping("/a")
public class ServiceAController {

    @Autowired
    private RestTemplate restTemplate;

    private static final String BASE_URL
            = "http://localhost:8081/";

    private static final String SERVICE_A = "serviceA";

    int count=1;

    @GetMapping("/{value}")
    @CircuitBreaker(name = SERVICE_A , fallbackMethod = "serviceAFallback")
    public String serviceA(@PathVariable("value") String value) {

        String url = BASE_URL + "b/"+value;
        if(value.equals("a")) {
        	throw new MyException("Business Exception Raised in Service A");
        }
        
        if(value.equals("b")) {
        	throw new MyException2("Business Exception2 Raised in Service A");
        }
        String response = restTemplate.getForObject(url,String.class);
        
        return response;
    }

    public String serviceAFallback(String value ,Exception e) throws Exception {
    	if(e.getClass().equals(MyException.class)) {
    		 throw e;
    	}
        return "This is a fallback method from Service A";
    }
}
