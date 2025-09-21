package com.rmp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class GreetSeviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(GreetSeviceApplication.class, args);
	}

}
