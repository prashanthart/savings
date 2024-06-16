package com.family.savings.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.family.savings.model.User;

@Repository
public interface SavingsRepository extends MongoRepository<User, String> {
	Optional<User> findByUserId(String userId);
	void deleteByUserId(String userId);
}
