package com.zyv1.databaseconn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class DatabaseConnApplication {

	public static void main(String[] args) {
		SpringApplication.run(DatabaseConnApplication.class, args);
	}

}
