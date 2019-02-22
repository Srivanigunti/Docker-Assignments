package com.moneymoney.app.accountsservice.receiver;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.listener.exception.ListenerExecutionFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.moneymoney.app.accountsservice.resource.AccountResource;
import com.moneymoney.app.transactionsservice.entity.Transaction;

@Component
public class Receiver {
	@Autowired
	private AccountResource resource;

	@Bean
	public Queue queue() {
		return new Queue("transactionQ",false);
	}
	
	@RabbitListener(queues="transactionQ")
	public void processUpdateBalance(Transaction transaction) throws ListenerExecutionFailedException{
		resource.updateBalance(transaction);
	}
}