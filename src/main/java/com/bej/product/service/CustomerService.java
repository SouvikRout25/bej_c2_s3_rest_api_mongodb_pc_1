package com.bej.product.service;

import com.bej.product.domain.Customer;
import com.bej.product.exception.CustomerNotFoundException;

import java.util.List;

public interface CustomerService {

    Customer saveCustomer(Customer customer);
    List<Customer> getAllCustomers() throws Exception;
    boolean deleteCustomer(int customerId) throws CustomerNotFoundException;
    List<Customer> getAllCustomersByProductName(String productName) throws CustomerNotFoundException;
}