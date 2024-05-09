package com.byeon.translator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class TranslatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(TranslatorApplication.class, args);
	}

}
