package com.rahkar.mtn.mtnadminconsole;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.rahkar")
public class MtnAdminConsoleApplication {

	public static void main(String[] args) {
		SpringApplication.run(MtnAdminConsoleApplication.class, args);
	}
}
