package com.kmeisl.schichtkalender;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SchichtkalenderApplication {

	private static final Logger log = LoggerFactory.getLogger(SchichtkalenderApplication.class);

	
	public static void main(String[] args) {
		SpringApplication.run(SchichtkalenderApplication.class, args);
	}

}
