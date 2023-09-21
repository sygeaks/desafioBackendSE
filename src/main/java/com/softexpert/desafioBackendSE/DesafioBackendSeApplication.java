package com.softexpert.desafioBackendSE;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class DesafioBackendSeApplication {

	public static void main(String[] args) {
		SpringApplication.run(DesafioBackendSeApplication.class, args);
	}

}
