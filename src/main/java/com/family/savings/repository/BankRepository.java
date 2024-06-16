package com.family.savings.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.family.savings.model.Bank;

@Repository
public interface BankRepository extends MongoRepository<Bank, String> {
	
  List<Bank> findByName(String name);
  Bank deleteByUserId(String id);

}
