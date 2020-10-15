package com.zyv1.warnserver;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@MapperScan("com.zyv1.warnserver.dao")
public class WarnServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(WarnServerApplication.class, args);
	}

}
