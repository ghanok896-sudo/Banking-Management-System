package com.bank.service;

import java.util.List;

import com.bank.entity.Transaction;

public interface TransactionService 
{
	
	 List<Transaction> getAllTransactions();

	    Transaction getTransactionById(Long id);

	    void deleteTransaction(Long id);
	    
	    

}
