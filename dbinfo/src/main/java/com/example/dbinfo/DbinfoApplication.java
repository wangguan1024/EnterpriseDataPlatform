package com.example.dbinfo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.dbinfo.dao")
public class DbinfoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DbinfoApplication.class, args);
	}

}
