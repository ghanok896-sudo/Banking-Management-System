package com.bank.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.dto.AccountDTO;
import com.bank.dto.TransactionDTO;
import com.bank.dto.TransferDTO;
import com.bank.entity.Account;
import com.bank.entity.Transaction;
import com.bank.service.AccountService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController 
{
	
	 private final AccountService accountService;
	 
	 public AccountController(AccountService accountService) {
	        this.accountService = accountService;
	 }

	    @PostMapping("/create")
	    public Account createAccount(
	            @RequestBody AccountDTO dto) {

	        return accountService.createAccount(dto);
	    }
	    
	    @GetMapping
	    public List<Account> getAllAccounts() {

	        return accountService.getAllAccounts();
	    }
	    
	    @GetMapping("/transactions/{accountNumber}")
	    public List<Transaction> getTransactions(
	            @PathVariable String accountNumber) {

	        return accountService
	                .getTransactionHistory(accountNumber);
	    }
	    
	    @PostMapping("/deposit")
	    public Account deposit(
	            @RequestBody TransactionDTO dto) {

	        return accountService.deposit(dto);
	    }
	    
	    @PostMapping("/withdraw")
	    public Account withdraw(
	            @RequestBody TransactionDTO dto) {

	        return accountService.withdraw(dto);
	    }

	    @PostMapping("/transfer")
	    public String transferMoney(
	            @RequestBody TransferDTO dto) {

	        return accountService.transferMoney(dto);
	    }

}
