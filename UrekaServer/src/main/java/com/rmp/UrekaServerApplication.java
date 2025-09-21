package com.rmp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class UrekaServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(UrekaServerApplication.class, args);
	}

}
