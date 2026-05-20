package com.bank.service;

import java.util.List;

import com.bank.dto.CustomerDTO;
import com.bank.entity.Customer;

public interface CustomerService 
{
	
	Customer createCustomer(CustomerDTO dto);

    List<Customer> getAllCustomers();

}
