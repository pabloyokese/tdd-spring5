package com.example.demo.web;

import com.example.demo.entities.Product;
import com.example.demo.service.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {

    @MockBean
    private ProductService service;

    @Autowired
    private MockMvc mockMvc;


    @Test
    @DisplayName("GET /product/1 - Found")
    public void testGetProductByIdFound() throws Exception{
        Product mockProduct = new Product(1l,"Product Name",10,1);
        doReturn(Optional.of(mockProduct)).when(service).findById(1l);

        mockMvc.perform(get("/product/{id}",1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(header().string(HttpHeaders.ETAG,"\"1\""))
                .andExpect(header().string(HttpHeaders.LOCATION,"/product/1"))

                .andExpect(jsonPath("$.id",is(1)))
                .andExpect(jsonPath("$.name",is("Product Name")))
                .andExpect(jsonPath("$.quantity",is(10)))
                .andExpect(jsonPath("$.version",is(1)));
    }

//    @Test
//    @DisplayName("GET /product/1 - Not Found")
//    public void testGetProductByIdNotFound() throws Exception{
//        doReturn(Optional.empty()).when(service).findById(1l);
//        mockMvc.perform(get("/product/{id}",1))
//                .andExpect(status().isNotFound());
//    }
//
//    @Test
//    @DisplayName("POST /product - Success")
//    public void testCreateProduct() throws Exception {
//        Product postProduct = new Product("Product Name", 10);
//    }
}
