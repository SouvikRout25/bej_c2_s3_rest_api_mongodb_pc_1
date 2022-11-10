package com.bej.product.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.bej.product.domain.Customer;
import com.bej.product.domain.Product;
import com.bej.product.exception.CustomerAlreadyExistException;
import com.bej.product.exception.CustomerNotFoundException;
import com.bej.product.service.CustomerServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private CustomerServiceImpl customerService;

    @InjectMocks
    private CustomerController customerController;
    private Customer customer;
    private Product product;
    List<Customer> customerList;

    @BeforeEach
    void setUp() {
        product = new Product(41, "Product41", "ProductDescription41");
        customer = new Customer(41, "Arjun41", 4141414, product);
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();

    }

    @AfterEach
    void tearDown() {
        customer = null;
    }

    @Test
    public void saveCustomerTest() throws Exception {
        when(customerService.saveCustomer(any())).thenReturn(customer);
        mockMvc.perform(post("/customer/api/customer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonToString(customer)))
                .andExpect(status().isCreated()).andDo(MockMvcResultHandlers.print());
        verify(customerService,times(1)).saveCustomer(any());

    }

    @Test
    public void saveCustomerFailure() throws Exception {
        when(customerService.saveCustomer(any())).thenThrow(CustomerAlreadyExistException.class);
        mockMvc.perform(post("/customer/api/customer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonToString(customer)))
                .andExpect(status().isConflict()).andDo(MockMvcResultHandlers.print());
        verify(customerService,times(1)).saveCustomer(any());
    }

    @Test
    public void deleteCustomer() throws Exception {
        when(customerService.deleteCustomer(anyInt())).thenReturn(true);
        mockMvc.perform(delete("/customer/api/customer/41")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonToString(customer)))
                .andExpect(status().is2xxSuccessful())
                .andDo(MockMvcResultHandlers.print());
        verify(customerService,times(1)).deleteCustomer(anyInt());

    }



    private static String jsonToString(final Object ob) throws JsonProcessingException {
        String result;
        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonContent = mapper.writeValueAsString(ob);
            result = jsonContent;
        } catch(JsonProcessingException e) {
            result = "JSON processing error";
        }

        return result;
    }

}
