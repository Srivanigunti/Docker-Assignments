package com.moneymoney.app.accountsservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import com.moneymoney.app.accountsservice.entity.CurrentAccount;
import com.moneymoney.app.accountsservice.entity.SavingsAccount;
import com.moneymoney.app.accountsservice.repository.AccountsRepository;

@EnableDiscoveryClient
@SpringBootApplication
public class AccountsServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountsServiceApplication.class, args);
	}

	@Bean
	public CommandLineRunner populateData(AccountsRepository accountsRepository) {
		return (arg) -> {
			accountsRepository.save(new SavingsAccount(101,"Srivani",10_000.00,true));
			accountsRepository.save(new SavingsAccount(102,"Kunni",25_000.00,true));
			accountsRepository.save(new SavingsAccount(103,"Venkat",35_000.00,false));
			accountsRepository.save(new CurrentAccount(104,"Aruna",40_000.00,5_000.00));
			accountsRepository.save(new CurrentAccount(105,"VarunSai",50_000.00,5_000.00));
			accountsRepository.save(new CurrentAccount(106,"Mani",60_000.00,5_000.00));
		};
	}
	
}
