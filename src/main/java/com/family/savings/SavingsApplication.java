package com.family.savings;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableMongoRepositories(basePackages = "com.family.savings.repository")
public class SavingsApplication {
	
	
	public static void main(String[] args) {
		SpringApplication.run(SavingsApplication.class, args);
		
		
	}

}
