package com.bank.service;

import java.util.List;

import com.bank.dto.AccountDTO;
import com.bank.dto.TransactionDTO;
import com.bank.dto.TransferDTO;
import com.bank.entity.Account;
import com.bank.entity.Transaction;

public interface AccountService {

	
	    Account createAccount(AccountDTO dto);

	    List<Account> getAllAccounts();
	    
	    List<Transaction> getTransactionHistory(String accountNumber);

	    Account deposit(TransactionDTO dto);

	    Account withdraw(TransactionDTO dto);

	    String transferMoney(TransferDTO dto);
}
