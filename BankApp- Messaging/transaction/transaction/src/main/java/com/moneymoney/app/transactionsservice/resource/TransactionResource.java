package com.moneymoney.app.transactionsservice.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.moneymoney.app.transactionsservice.entity.Transaction;
import com.moneymoney.app.transactionsservice.sender.Sender;
import com.moneymoney.app.transactionsservice.service.TransactionService;

@RefreshScope
@RestController
@RequestMapping("/transactions")
public class TransactionResource {

	@Autowired
	private TransactionService service;
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private Sender sender;

	@PostMapping("/deposit")
	public ResponseEntity<Transaction> deposit(@RequestBody Transaction transaction) {
		ResponseEntity<Double> entity = restTemplate.getForEntity(
				"http://accounts-service/accounts/" + transaction.getAccountNumber() + "/balance", Double.class);
		Double currentBalance = entity.getBody();
		Double updateBalance = service.deposit(transaction.getAccountNumber(), transaction.getTransactionDetails(),
				currentBalance, transaction.getAmount());
		transaction.setCurrentBalance(updateBalance);
		/*
		 * restTemplate.put( "http://accounts-service/accounts/" +
		 * transaction.getAccountNumber() + "?currentBalance=" + updateBalance, null);
		 */
		sender.updateBalance(transaction);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@PostMapping("/withdraw")
	public ResponseEntity<Transaction> withdraw(@RequestBody Transaction transaction) {
		ResponseEntity<Double> entity = restTemplate.getForEntity(
				"http://accounts-service/accounts/" + transaction.getAccountNumber() + "/balance", Double.class);
		Double currentBalance = entity.getBody();
		Double updateBalance = service.withdraw(transaction.getAccountNumber(), transaction.getTransactionDetails(),
				currentBalance, transaction.getAmount());
		transaction.setCurrentBalance(updateBalance);
		/*
		 * restTemplate.put( "http://accounts-service/accounts/" +
		 * transaction.getAccountNumber() + "?currentBalance=" + updateBalance, null);
		 */
		sender.updateBalance(transaction);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@GetMapping("/statement")
	public ResponseEntity<CurrentDataSet> getStatement() {
		CurrentDataSet currentDataSet = new CurrentDataSet();
		List<Transaction> transactions = service.getStatement();
		currentDataSet.setTransactions(transactions);
		return new ResponseEntity<CurrentDataSet>(currentDataSet,HttpStatus.OK);
	}
}