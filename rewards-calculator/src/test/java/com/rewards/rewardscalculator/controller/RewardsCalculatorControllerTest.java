package com.rewards.rewardscalculator.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rewards.rewardscalculator.dto.RewardsDto;
import com.rewards.rewardscalculator.dto.Transaction;
import com.rewards.rewardscalculator.service.RewardsCalculatorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@WebMvcTest(RewardsCalculatorController.class)
public class RewardsCalculatorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RewardsCalculatorService rewardsCalculatorService;

    @Test
    void testGetRewardsForCustomerId() throws Exception {
        RewardsDto rewardsDto = new RewardsDto();
        rewardsDto.setCustomerId(1L);
        rewardsDto.setTotalRewards(100L);
        when(rewardsCalculatorService.calculateRewardsForCustomerId(anyLong())).thenReturn(rewardsDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.customerId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalRewards").value(100));
    }

    @Test
    public void testCreateTransaction_Success() throws Exception {
        // Create a sample transaction
        Transaction transaction = new Transaction();
        transaction.setCustomerId(1L);
        transaction.setTransactionAmount(120.0);

        // Convert transaction to JSON
        String transactionJson = objectMapper.writeValueAsString(transaction);

        // Perform POST request
        mockMvc.perform(MockMvcRequestBuilders.post("/api/transaction")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(transactionJson))
                // Expect HTTP 200 OK
                .andExpect(MockMvcResultMatchers.status().isOk())
                // Expect response content "success"
                .andExpect(MockMvcResultMatchers.content().string("success"));

        // Verify that rewardsCalculatorService.createTransaction was called with the transaction
        //verify(rewardsCalculatorService).createTransaction(transaction);
    }

    @Test
    public void testCreateTransaction_InvalidTransaction() throws Exception {
        // Create a sample invalid transaction (e.g., missing required fields)
        Transaction invalidTransaction = new Transaction();

        // Convert invalid transaction to JSON
        String invalidTransactionJson = objectMapper.writeValueAsString(invalidTransaction);

        // Perform POST request with invalid JSON
        mockMvc.perform(MockMvcRequestBuilders.post("/api/transaction")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidTransactionJson))
                // Expect HTTP 400 Bad Request due to validation failure
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }


}
