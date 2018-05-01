package com.example.infoRetrieval;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.infoRetrieval.dao")
public class InfoRetrievalApplication {

	public static void main(String[] args) {
		SpringApplication.run(InfoRetrievalApplication.class, args);
	}
}
