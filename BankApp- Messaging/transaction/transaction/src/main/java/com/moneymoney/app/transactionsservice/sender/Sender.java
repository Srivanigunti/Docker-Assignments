package com.moneymoney.app.transactionsservice.sender;

import org.springframework.amqp.core.Queue;
import org.springframework.stereotype.Component;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import com.moneymoney.app.transactionsservice.entity.Transaction;

@Component
public class Sender {

	@Autowired
	RabbitMessagingTemplate template;
	
	@Bean
	public Queue queue() {
		return new Queue("transactionQ",false);
	}
	
	public void updateBalance(Transaction transaction) {
		template.convertAndSend("transactionQ",transaction);
	}
}