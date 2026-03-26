package com.rewards;

import com.rewards.entity.Transaction;
import com.rewards.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RewardsControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private TransactionRepository repository;

    @LocalServerPort
    private int port;

    private String baseUrl;
   
    @BeforeEach
    void setup() {
        baseUrl = "http://localhost:" + port + "/api/v1/rewards";

        repository.deleteAll();

        repository.save(new Transaction(null, 1L, BigDecimal.valueOf(120), LocalDate.of(2024, 1, 10)));
        repository.save(new Transaction(null, 1L, BigDecimal.valueOf(75), LocalDate.of(2024, 2, 5)));
        repository.save(new Transaction(null, 2L, BigDecimal.valueOf(200), LocalDate.of(2024, 1, 15)));
    }

    // Get rewards for all customers
    @Test
    void testGetAllRewards_Success() {

        ResponseEntity<Object> response =
                restTemplate.getForEntity(baseUrl, Object.class);

        assertEquals(200, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    //Test for Invalid URL
    @Test
    void testGetAllRewards_InvalidUrl() {

        ResponseEntity<Object> response =
                restTemplate.getForEntity(baseUrl + "s", Object.class);

        assertEquals(404, response.getStatusCode());
    }
    
    // Test to check : Get rewards for specific customer
    @Test
    void testGetRewardsByCustomer_Success() {

        ResponseEntity<Object> response =
                restTemplate.getForEntity(baseUrl + "/1", Object.class);

        assertEquals(200, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    //Test for  Customer not found
    @Test
    void testGetRewardsByCustomer_NotFound() {

        ResponseEntity<Object> response =
                restTemplate.getForEntity(baseUrl + "/999", Object.class);

        assertEquals(404, response.getStatusCode());
    }
}