package com.moneymoney.app.transactionsservice.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moneymoney.app.transactionsservice.entity.Transaction;
import com.moneymoney.app.transactionsservice.entity.TransactionType;
import com.moneymoney.app.transactionsservice.repository.TransactionRepository;

@Service
public class TransactionServiceImpl implements TransactionService {
	@Autowired
	private TransactionRepository repository;

	@Override
	public Double withdraw(int accountNumber,String transactioDetails,double currentBalance, double amount) {
		Transaction transaction = new Transaction();
		transaction.setAccountNumber(accountNumber);
		transaction.setAmount(amount);
		currentBalance = currentBalance-amount;
		transaction.setCurrentBalance(currentBalance);
		transaction.setTransactionDate(LocalDateTime.now());
		transaction.setTransactionType(TransactionType.WITHDRAW);
		repository.save(transaction);
		return currentBalance;

	}

	@Override
	public Double deposit(int accountNumber,String transactioDetails,double currentBalance, double amount) {
		Transaction transaction = new Transaction();
		transaction.setAccountNumber(accountNumber);
		transaction.setAmount(amount);
		currentBalance = currentBalance+amount;
		transaction.setCurrentBalance(currentBalance);
		transaction.setTransactionDate(LocalDateTime.now());
		transaction.setTransactionType(TransactionType.DEPOSIT);
		repository.save(transaction);
		return currentBalance;
	}
	
	@Override
	public List<Transaction> getStatement(LocalDate startDate,LocalDate endDate){
		return null;
		
	}

	@Override
	public List<Transaction> getStatement() {
		return repository.findAll();
	}
}