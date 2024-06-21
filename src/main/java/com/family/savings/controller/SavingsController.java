package com.family.savings.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.family.savings.model.Bank;
import com.family.savings.model.User;
import com.family.savings.service.SavingsServiceImpl;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class SavingsController {
	
	@Autowired
	private SavingsServiceImpl savingsServiceImpl;
	
	@PostMapping("/addUser")
	public ResponseEntity<Map<String,String>> addUser(@RequestBody User user) {
	
			String response = savingsServiceImpl.addUser(user);
			Map<String,String> responseMap = new HashMap<>();
			responseMap.put("message", response);
			return new ResponseEntity<>(responseMap,HttpStatus.OK);
	}
	
	@GetMapping("/getUsers")
	public ResponseEntity<List<User>> getUsers(){
		
		List<User> allUsers = savingsServiceImpl.getAllUsers();
		return ResponseEntity.ok(allUsers);
		
	}
	
	@PostMapping("/addBank/{userId}")
	public ResponseEntity<Map<String,String>> addBankToUser(@PathVariable String userId,@RequestBody Bank newBank){
		
		String response = savingsServiceImpl.addBanksToUser(userId, newBank);
		Map<String,String> responseMap = new HashMap<>();
		responseMap.put("message", response);
		return new ResponseEntity<>(responseMap,HttpStatus.OK);
		
	}
	
	@PostMapping("/addAmountToBank/{userId}/{bankName}")
	public ResponseEntity<Map<String,String>> addAmountToBank(@PathVariable String userId,@PathVariable String bankName,@RequestParam Double amount) {
		String responseString =  savingsServiceImpl.addAmountToBank(userId, bankName, amount);
		Map<String,String> response = new HashMap<>();
		response.put("message", responseString);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}

	
	@DeleteMapping("/deleteUser/{userId}")
	public ResponseEntity<Map<String,String>> deleteUser(@PathVariable String userId) {
		String responseString = savingsServiceImpl.deleteUser(userId);
		Map<String,String> response = new HashMap<>();
		response.put("message", responseString);
		return new ResponseEntity<>(response,HttpStatus.OK);
		
		
	}
	
	@DeleteMapping("/deleteBank/{userId}")
	public ResponseEntity<Map<String,String>> deleteBankForUser(@PathVariable String userId,@RequestParam String bankName){

		String responseString = savingsServiceImpl.deleteBankForUser(userId, bankName);
		Map<String,String> response = new HashMap<>();
		response.put("message", responseString);
		return new ResponseEntity<>(response,HttpStatus.OK);
		
		
	}
	
	
}
