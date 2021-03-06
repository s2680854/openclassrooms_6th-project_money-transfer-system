package com.openclassrooms.moneytransfersystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.moneytransfersystem.model.Tax;
import com.openclassrooms.moneytransfersystem.model.User;
import com.openclassrooms.moneytransfersystem.service.tax.TaxCreationService;
import com.openclassrooms.moneytransfersystem.service.tax.TaxDeletionService;
import com.openclassrooms.moneytransfersystem.service.tax.TaxReadService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TaxControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private TaxReadService taxReadService;
    @Autowired
    private TaxCreationService taxCreationService;
    @Autowired
    private TaxDeletionService taxDeletionService;

    @Test
    public void shouldCreateTax() throws Exception {

        Tax tax = new Tax();
        tax.setName("Test");
        tax.setRate(0.01);

        mockMvc.perform(post("/createTax")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(tax)))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldCreateTaxes() throws Exception {

        Tax tax = new Tax();
        tax.setName("Test2");
        tax.setRate(0.01);

        List<Tax> taxes = new ArrayList<>();
        taxes.add(tax);

        mockMvc.perform(post("/createTaxes")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(taxes)))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldGetTaxById() throws Exception {

        taxDeletionService.deleteTaxes();

        Tax tax = new Tax();
        tax.setName("DEFAULT");
        tax.setRate(0.005);
        taxCreationService.createTax(tax);

        Long taxId = taxReadService.readTaxByName("DEFAULT").getId();

        mockMvc.perform(get("/taxes/" + taxId)).andExpect(status().isOk());
    }

    @Test
    public void shouldUpdateTax() throws Exception {

        Tax tax = new Tax();
        tax.setId(1L);
        tax.setName("DEFAULT");
        tax.setRate(0.005);

        mockMvc.perform(put("/updateTax")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(tax)))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldDeleteTax() throws Exception {

        mockMvc.perform(delete("/taxes/2")).andExpect(status().isOk());
    }

    @Test
    public void shouldDeleteTaxes() throws Exception {

        mockMvc.perform(delete("/taxes")).andExpect(status().isOk());
    }
}
