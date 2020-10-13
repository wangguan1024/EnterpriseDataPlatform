package com.zyv1.databasemanager;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.zyv1.databasemanager.dao")
public class DatabaseManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(DatabaseManagerApplication.class, args);
	}

}
