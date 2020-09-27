package com.epsglobal.services;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.epsglobal.services")
@EnableJpaRepositories(basePackages = "com.epsglobal.services.dataaccess")
@EntityScan(basePackages = "com.epsglobal.services.domain")
public class Application {
	//comentario de prueba
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
