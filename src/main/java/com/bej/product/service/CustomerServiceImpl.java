package com.bej.product.service;

import com.bej.product.domain.Customer;
import com.bej.product.exception.CustomerNotFoundException;
import com.bej.product.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CustomerServiceImpl implements CustomerService{
    private CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public List<Customer> getAllCustomers() throws Exception {
        return customerRepository.findAll();
    }

    @Override
    public boolean deleteCustomer(int customerId) throws CustomerNotFoundException {
        boolean flag = false;
        if(customerRepository.findById(customerId).isEmpty()){
            throw new CustomerNotFoundException();
        }
        else{
            customerRepository.deleteById(customerId);
            flag=true;
        }
        return flag;
    }

    @Override
    public List<Customer> getAllCustomersByProductName(String productName) throws CustomerNotFoundException {
        if(customerRepository.findAllCustomersFromProductName(productName).isEmpty()){
            throw new CustomerNotFoundException();
        }
        return customerRepository.findAllCustomersFromProductName(productName);
    }
}