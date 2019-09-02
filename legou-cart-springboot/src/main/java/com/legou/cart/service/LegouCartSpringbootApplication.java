package com.legou.cart.service;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.legou.mapper")
public class LegouCartSpringbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(LegouCartSpringbootApplication.class, args);

	}
	

}
