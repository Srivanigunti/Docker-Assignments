package com.moneymoney.app.accountsservice.resource;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.moneymoney.app.accountsservice.entity.Accounts;
import com.moneymoney.app.accountsservice.service.AccountsService;
import com.moneymoney.app.transactionsservice.entity.Transaction;

@RefreshScope
@RestController
@RequestMapping("/accounts")
public class AccountResource {
	
	@Autowired
	private AccountsService service;
	
	@GetMapping
	public ResponseEntity<List<Accounts>> getAllAccounts(){
		List<Accounts> accounts=service.getallAccounts();
		return new ResponseEntity<>(accounts, HttpStatus.OK);
	}
	
	@GetMapping("/{accountNumber}")
	public ResponseEntity<Accounts> getAccountbyId(@PathVariable int accountNumber){
		Optional<Accounts> optional = service.getAccountById(accountNumber);
		Accounts account = optional.get();
		return new ResponseEntity<Accounts>(account, HttpStatus.OK);
	}

	@GetMapping("/{accountNumber}/balance")
	public ResponseEntity<Double> getCurrentBalance(@PathVariable int accountNumber){
		Optional<Accounts> optional = service.getAccountById(accountNumber);
		double currentBalance = optional.get().getCurrentBalance();
		if(optional.get()==null) {
			return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(currentBalance,HttpStatus.OK);
	}
	@PutMapping("/{accountNumber}")
	public ResponseEntity<Accounts> updateAccountBalance(@PathVariable int accountNumber,@RequestParam("currentBalance")Double currentBalance){
		Optional<Accounts> optional = service.getAccountById(accountNumber);
		Accounts accounts =  optional.get();
		if(accounts==null) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		
		accounts.setCurrentBalance(currentBalance);
		service.updateBalance(accounts);
		return new ResponseEntity<Accounts>(accounts, HttpStatus.OK);
		
	}
	public void updateBalance(Transaction transaction) {
		Optional<Accounts> optional = service.getAccountById(transaction.getAccountNumber());
		Accounts accounts = optional.get();
		accounts.setCurrentBalance(transaction.getCurrentBalance());
		service.updateBalance(accounts);		
	}
}