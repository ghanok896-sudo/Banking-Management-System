package com.bank.controller;

	import com.bank.dto.CustomerDTO;
	import com.bank.entity.Customer;
import com.bank.service.CustomerService;

import lombok.RequiredArgsConstructor;

	import org.springframework.web.bind.annotation.*;

	import java.util.List;

	@RestController
	@RequestMapping("/api/customers")
	@RequiredArgsConstructor
	public class CustomerController {

	    private final CustomerService customerService;
	    
	    public CustomerController(CustomerService customerService) {

	        this.customerService = customerService;
	    }

	    @PostMapping
	    public Customer createCustomer(
	            @RequestBody CustomerDTO dto) {

	        return customerService.createCustomer(dto);
	    }

	    @GetMapping
	    public List<Customer> getAllCustomers() {

	        return customerService.getAllCustomers();
	    }
	}


