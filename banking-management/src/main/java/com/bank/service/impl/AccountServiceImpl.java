package com.bank.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.bank.dto.AccountDTO;
import com.bank.dto.TransactionDTO;
import com.bank.dto.TransferDTO;
import com.bank.entity.Account;
import com.bank.entity.Customer;
import com.bank.entity.Transaction;
import com.bank.exception.InsufficientBalanceException;
import com.bank.exception.ResourceNotFoundException;
import com.bank.repository.AccountRepository;
import com.bank.repository.CustomerRepository;
import com.bank.repository.TransactionRepository;
import com.bank.service.AccountService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService
{
	
	private final AccountRepository accountRepository;
	
	private final CustomerRepository customerRepository;
	
	private final TransactionRepository transactionRepository;
	
	public AccountServiceImpl(AccountRepository accountRepository,
            CustomerRepository customerRepository,
            TransactionRepository transactionRepository) {

this.accountRepository = accountRepository;
this.customerRepository = customerRepository;
this.transactionRepository = transactionRepository;
	}
	
	public Account createAccount(AccountDTO dto)
	{
		Customer customer=customerRepository.findById(dto.getCustomerId())
				.orElseThrow(()->
				new ResourceNotFoundException("Customer not found"));
		
		Account account = new Account();

        account.setAccountType(dto.getAccountType());

        account.setBalance(dto.getBalance());

        account.setAccountNumber(generateAccountNumber());

        account.setCustomer(customer);

        return accountRepository.save(account);
    }
	
	 @Override
	    public List<Account> getAllAccounts() {

	        return accountRepository.findAll();
	    }

	 
	 @Override
	 public List<Transaction> getTransactionHistory(
	         String accountNumber) {

	     return transactionRepository
	             .findByAccount_AccountNumber(accountNumber);
	 }
	 
	 @Override
	    public Account getAccountById(Long id) {

	        Account account = accountRepository.findById(id)
	                .orElseThrow(() ->
	                        new ResourceNotFoundException("Account not found with id : " + id));

	        return account;
	    }
	 
	 @Override
	 public void deleteAccount(Long accountId) {

	     Account account = accountRepository
	             .findById(accountId)
	             .orElseThrow(() ->
	                     new ResourceNotFoundException("Account not found"));

	     accountRepository.delete(account);
	 }

	 @Override
	    public Account deposit(TransactionDTO dto) {

	        Account account = accountRepository
	                .findByAccountNumber(dto.getAccountNumber())
	                .orElseThrow(() ->
	                        new ResourceNotFoundException("Account not found"));

	        account.setBalance(
	                account.getBalance().add(dto.getAmount()));

	        saveTransaction(account,
	                dto.getAmount(),
	                "DEPOSIT",
	                dto.getRemarks());

	        return accountRepository.save(account);
	    }
	 
	 
	 @Override
	    public Account withdraw(TransactionDTO dto) {

	        Account account = accountRepository
	                .findByAccountNumber(dto.getAccountNumber())
	                .orElseThrow(() ->
	                        new ResourceNotFoundException("Account not found"));

	        if(account.getBalance().compareTo(dto.getAmount()) < 0) {

	            throw new InsufficientBalanceException(
	                    "Insufficient Balance");
	        }
	        
	        account.setBalance(
	                account.getBalance().subtract(dto.getAmount()));

	        saveTransaction(account,
	                dto.getAmount(),
	                "WITHDRAW",
	                dto.getRemarks());

	        return accountRepository.save(account);
	    }
	 
	 @Override
	    public String transferMoney(TransferDTO dto) {

	        Account fromAccount = accountRepository
	                .findByAccountNumber(dto.getFromAccount())
	                .orElseThrow(() ->
	                        new ResourceNotFoundException("Sender account not found"));
	        
	        Account toAccount = accountRepository
	                .findByAccountNumber(dto.getToAccount())
	                .orElseThrow(() ->
	                        new ResourceNotFoundException("Receiver account not found"));
	        
	        if(fromAccount.getBalance().compareTo(dto.getAmount()) < 0) {

	            throw new InsufficientBalanceException(
	                    "Insufficient balance");
	        }
	        
	        fromAccount.setBalance(
	                fromAccount.getBalance().subtract(dto.getAmount()));

	        toAccount.setBalance(
	                toAccount.getBalance().add(dto.getAmount()));
	        
	        accountRepository.save(fromAccount);

	        accountRepository.save(toAccount);

	        saveTransaction(fromAccount,
	                dto.getAmount(),
	                "TRANSFER_DEBIT",
	                "Transferred to " + dto.getToAccount());
	        saveTransaction(toAccount,
	                dto.getAmount(),
	                "TRANSFER_CREDIT",
	                "Received from " + dto.getFromAccount());

	        return "Money transferred successfully";
	    }
	 
	 
	 private void saveTransaction(Account account,
             BigDecimal amount,
             String type,
             String remarks) {
		 
		 Transaction transaction = new Transaction();

	        transaction.setAccount(account);

	        transaction.setAmount(amount);

	        transaction.setTransactionType(type);

	        transaction.setRemarks(remarks);

	        transaction.setTransactionDate(LocalDateTime.now());

	        transactionRepository.save(transaction);
	    }
	 
	 
private String generateAccountNumber() {

    return "ACC" + new Random().nextInt(999999);
}
	}
	


	
	
    
        




