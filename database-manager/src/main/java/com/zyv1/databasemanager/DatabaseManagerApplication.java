package com.zyv1.databasemanager;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@MapperScan("com.zyv1.databasemanager.dao")
@EnableFeignClients
public class DatabaseManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(DatabaseManagerApplication.class, args);
	}

}
