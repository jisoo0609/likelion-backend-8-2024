package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

// @EnableAutoConfiguration
// 이 클래스를 Spring Boot로서 자동 설정 하게하는 어노테이션

// @ComponentScan
// 이 클래스를 기준으로 Bean 객체를 검색하게 하는 어노테이션

// @SpringBootApplication
// @EnableAutoConfiguration + @ComponentScan
@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		// 현재 실행중인 IoC Container를 반환한다
		ApplicationContext applicationContext = SpringApplication.run(DemoApplication.class, args);
		// IoC Container가 관리하고 있는 Bean 객체를 확인한다
		for (String beanName : applicationContext.getBeanDefinitionNames()) {
			System.out.println(beanName);
		}

//		SpringApplication.run(DemoApplication.class, args);


	}

}
