package com.family.savings.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.family.savings.model.Bank;
import com.family.savings.model.User;
import com.family.savings.repository.BankRepository;
import com.family.savings.repository.SavingsRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SavingsServiceImpl {
	

	@Autowired
	private SavingsRepository savingsRepository;
	
	@Autowired
	private BankRepository bankRepository;
	
	@Transactional
	public String addUser(User user) {
		Optional<User> optionalUser =  savingsRepository.findByUserId(user.getUserId());
		if(optionalUser.isPresent()) {
			return "User already exist";
		}
		List<Bank> banks = user.getBank();
		banks.forEach(bank->bank.setUserId(user.getUserId()));
		
		List<Bank> savedBanks=bankRepository.saveAll(user.getBank());
		user.setBank(savedBanks);
		User savedUser = savingsRepository.save(user);
		if(savedUser==null) {
			return "failed in saving User";
		}
		return "User saved";
	}
	
	public List<User> getAllUsers(){
		List<User> users = savingsRepository.findAll();
		return users;
		
	}
	
	public String addBanksToUser(String userId,Bank newBank) {
		
		if(validateBankDetails(newBank)) {
			return "Invalid Bank details";
		}
		Optional<User> optionalUser = savingsRepository.findByUserId(userId);
		if(!optionalUser.isPresent()) {
			return "User does not found";
		}
		
			User user = optionalUser.get();
			if(user.getBank()== null) {
				List<Bank> createdBank = new ArrayList<>();
				newBank.setId(userId);
				createdBank.add(newBank);
				user.setBank(createdBank);
			}
			else {
				List<Bank> banks = user.getBank();
				banks = banks
						.stream()
						.filter(bank->bank.getName().trim().equalsIgnoreCase(newBank.getName().trim()))
						.collect(Collectors.toList());
				if(!banks.isEmpty()) {
					return "Bank already exist";
				}
				user.getBank().add(newBank);
			}
			addUser(user);
			return "New Bank added to the user : "+user.getName();
		
	}
	
	public String addAmountToBank(String userId,String bankName,Double amount) {
		
		User existingUser = savingsRepository.findByUserId(userId).get();
		List<Bank> banks = existingUser.getBank();
		
		OptionalInt indexOpt = IntStream.range(0,banks.size()).filter(i->banks.get(i).getName().equalsIgnoreCase(bankName)).findFirst();
		if(indexOpt.isPresent()) {
			int index = indexOpt.getAsInt();
			Bank bank = banks.get(index);
			bank.setAmount(bank.getAmount()+amount);
			bankRepository.save(bank);
			existingUser.getBank().set(index, bank);
			savingsRepository.save(existingUser);
			return "Amount added to the user : "+existingUser.getName();
		}
		return "Failed to add amount";
		
		
	}
	
	
	public String deleteUser(String userId) {
	  User user = savingsRepository.findByUserId(userId).get();
	  log.info("user {}",user);
	  user.getBank().forEach(bank->bankRepository.deleteByUserId(userId));
	  savingsRepository.deleteByUserId(user.getUserId());
	  return "user deleted";
//	  bankRepository.deleteById(user.);
	}
	public User getUser(String userId) {
		return savingsRepository.findByUserId(userId).get();
	}
	
	public String deleteBankForUser(String userId,String bankName) {
		User user = getUser(userId);
//		Bank savedBank = user.getBank().stream().filter(bank->bank.getName().equalsIgnoreCase(bankName)).findFirst().get();
		List<Bank> banks = user.getBank();
		OptionalInt indexOpt = IntStream.range(0, banks.size()).filter(i->banks.get(i).getName().equalsIgnoreCase(bankName)).findFirst();
		if(indexOpt.isPresent()) {
			int index = indexOpt.getAsInt();
			Bank bank = banks.get(index);
			bankRepository.delete(bank);
			user.getBank().remove(index);
			savingsRepository.save(user);
			return bank.getName()+" is deleted for "+user.getName();
			
		}
		
		return "Failed to delete a bank";
		
	}
	
	public boolean validateBankDetails(Bank bank) {
		
		
		if(bank.getName()== null || bank.getName().trim().isBlank() || bank.getAmount()==null) {
			return true;
		}
		return false;
		
	}

}
