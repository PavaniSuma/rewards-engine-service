package com.rewards;

import com.rewards.entity.Transaction;
import com.rewards.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class RewardsIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TransactionRepository repository;

    //
    @Test
    void testGetAllRewards_Success() throws Exception {
        mockMvc.perform(get("/api/v1/rewards")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].customerId").value(1))
                .andExpect(jsonPath("$[0].totalPoints").exists())
                .andExpect(jsonPath("$[1].customerId").value(2));
    }

    //For Invalid URl
    @Test
    void testGetAllRewards_InvalidUrl() throws Exception {
        mockMvc.perform(get("/api/v1/reward")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    //Test to Get rewards by customerId for valid customer
    @Test
    void testGetRewardsByCustomer_Success() throws Exception {
        mockMvc.perform(get("/api/v1/rewards/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId").value(1))
                .andExpect(jsonPath("$.totalPoints").exists())
                .andExpect(jsonPath("$.monthlyPoints.length()").value(3));
    }

    // Test when Customer not found
    @Test
    void testGetRewardsByCustomer_NotFound() throws Exception {
        mockMvc.perform(get("/api/v1/rewards/999")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}