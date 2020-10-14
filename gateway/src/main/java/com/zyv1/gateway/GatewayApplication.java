package com.zyv1.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication
@EnableWebFlux
public class GatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}


	@Bean
	public CorsFilter corsFilter() {
		//1.添加CORS配置信息
		CorsConfiguration config = new CorsConfiguration();
		//放行所有域
		config.addAllowedOrigin("*");
		//是否发送Cookie信息
		config.setAllowCredentials(true);
		//放行所有请求方式
		config.addAllowedMethod("*");
		//放行所有头部信息
		config.addAllowedHeader("*");
		//暴露所有头部信息（因为跨域访问默认不能获取全部头部信息）
		config.addExposedHeader("*");

		//2.添加映射路径
		UrlBasedCorsConfigurationSource configSource = new UrlBasedCorsConfigurationSource();
		configSource.registerCorsConfiguration("/**", config);

		//3.返回新的CorsFilter.
		return new CorsFilter(configSource);
	}

}
