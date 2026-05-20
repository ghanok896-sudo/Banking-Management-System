package com.bank.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bank.dto.CustomerDTO;
import com.bank.entity.Customer;
import com.bank.repository.CustomerRepository;
import com.bank.service.CustomerService;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService 
{
	private final CustomerRepository customerRepository;
	
	public CustomerServiceImpl(CustomerRepository customerRepository) {

        this.customerRepository = customerRepository;
    }

    @Override
    public Customer createCustomer(CustomerDTO dto) {

        Customer customer = new Customer();

        customer.setCustomerName(dto.getCustomerName());
        customer.setEmail(dto.getEmail());
        customer.setMobileNumber(dto.getMobileNumber());
        customer.setAddress(dto.getAddress());

        return customerRepository.save(customer);
    }
    
    @Override
    public List<Customer> getAllCustomers() {

        return customerRepository.findAll();
    }

}
