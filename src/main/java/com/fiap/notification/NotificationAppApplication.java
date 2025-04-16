package com.fiap.notification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class NotificationAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotificationAppApplication.class, args);
	}

}
