package com.bej.product.controller;

import com.bej.product.domain.Customer;
import com.bej.product.exception.CustomerNotFoundException;
import com.bej.product.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/custData/")
public class CustomerController {
    private CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }
    @PostMapping(value = "/customer")
    public ResponseEntity<?> saveCustomer(@RequestBody Customer customer){
        return new ResponseEntity<>(customerService.saveCustomer(customer), HttpStatus.CREATED);
    }
    @GetMapping(value = "/customers")
    public ResponseEntity<?> getAllCustomers(){
        ResponseEntity responseEntity =null;
        try {
            responseEntity= new ResponseEntity<>(customerService.getAllCustomers(),HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseEntity;
    }
    @DeleteMapping(value = "/customer/{customerId}")
    public ResponseEntity<?> deleteCustomer(@PathVariable int customerId) throws CustomerNotFoundException {
        ResponseEntity responseEntity =null;
        try {
            customerService.deleteCustomer(customerId);
            responseEntity = new ResponseEntity<>("successfully deleted one record",HttpStatus.OK);
        } catch (CustomerNotFoundException e) {
            throw new CustomerNotFoundException();
        }catch (Exception e){
            responseEntity=new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }
    @GetMapping(value = "/customer/{productName}")
    public ResponseEntity<?> getAllCustomersByProductName(@PathVariable String productName) throws CustomerNotFoundException{
        ResponseEntity responseEntity=null;
        try{
            responseEntity=new ResponseEntity(customerService.getAllCustomersByProductName(productName),HttpStatus.OK);

        } catch (CustomerNotFoundException e) {
            //responseEntity=new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
            throw new CustomerNotFoundException();
        }
        return responseEntity;
    }
}