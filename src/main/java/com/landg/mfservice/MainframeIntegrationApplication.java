package com.landg.mfservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("classpath:applicationContext.xml")
public class MainframeIntegrationApplication {

	public static void main(String[] args) {
		SpringApplication.run(MainframeIntegrationApplication.class, args);
	}
}
