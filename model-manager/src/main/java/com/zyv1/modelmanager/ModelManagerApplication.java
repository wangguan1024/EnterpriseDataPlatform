package com.zyv1.modelmanager;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.zyv1.modelmanager.dao")
public class ModelManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ModelManagerApplication.class, args);
	}

}
