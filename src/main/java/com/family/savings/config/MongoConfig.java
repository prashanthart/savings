package com.family.savings.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

@Configuration
public class MongoConfig {
	
	@Bean
	public MongoClient mongoClient(){
		return MongoClients.create();
	}

	
	 @Bean
	 public MongoTemplate mongoTemplate() {
	        return new MongoTemplate(mongoClient(), "mySavings");
	 }
	
	
	

}
