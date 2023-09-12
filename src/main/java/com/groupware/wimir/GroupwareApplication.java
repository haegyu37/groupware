package com.groupware.wimir;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class GroupwareApplication {

	public static void main(String[] args) {
		SpringApplication.run(GroupwareApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		System.out.println("어플리케이션 cors 들어옴 ");
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
						.allowedOrigins("http://211.252.85.109:3000")
						.allowedMethods("OPTIONS", "GET", "POST", "PUT", "DELETE");
			}
		};
	}

}
