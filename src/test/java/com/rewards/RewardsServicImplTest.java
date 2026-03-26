package com.rewards;

import com.rewards.dto.CustomerRewardsResponse;
import com.rewards.entity.Transaction;
import com.rewards.exceptions.ResourceNotFoundException;
import com.rewards.repository.TransactionRepository;
import com.rewards.service.RewardsServiceImpl;
import com.rewards.util.RewardsCalculator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RewardsServicImplTest {

    @Mock
    private TransactionRepository repository;

    @Mock
    private RewardsCalculator calculator;

    @InjectMocks
    private RewardsServiceImpl service;

    //Test getRewardsByCustomer - Success
    @Test
    void testGetRewardsByCustomer_Success() {

        Long customerId = 1L;

        List<Transaction> transactions = List.of(
                new Transaction(null, 1L, BigDecimal.valueOf(120), LocalDate.of(2024, 1, 10)),
                new Transaction(null, 1L, BigDecimal.valueOf(75), LocalDate.of(2024, 2, 5))
        );

        when(repository.findByCustomerId(customerId)).thenReturn(transactions);
        when(calculator.calculate(BigDecimal.valueOf(120))).thenReturn(90);
        when(calculator.calculate(BigDecimal.valueOf(75))).thenReturn(25);

        CustomerRewardsResponse response = service.getRewardsByCustomer(customerId);

        assertNotNull(response);
        assertEquals(customerId, response.getCustomerId());
        assertEquals(115, response.getTotalPoints()); // 90 + 25
        assertEquals(2, response.getMonthlyPoints().size());
    }

    // Test when Customer not found
    @Test
    void testGetRewardsByCustomer_NotFound() {

        Long customerId = 1L;

        when(repository.findByCustomerId(customerId)).thenReturn(List.of());

        assertThrows(ResourceNotFoundException.class,
                () -> service.getRewardsByCustomer(customerId));
    }

    //  Test getAllRewards - Success
    @Test
    void testGetAllRewards_Success() {

        List<Transaction> transactions = List.of(
                new Transaction(null, 1L, BigDecimal.valueOf(120), LocalDate.of(2024, 1, 10)),
                new Transaction(null, 2L, BigDecimal.valueOf(200), LocalDate.of(2024, 1, 15))
        );

        when(repository.findAll()).thenReturn(transactions);
        when(calculator.calculate(BigDecimal.valueOf(120))).thenReturn(90);
        when(calculator.calculate(BigDecimal.valueOf(200))).thenReturn(250);

        List<CustomerRewardsResponse> responseList = service.getAllRewards();

        assertNotNull(responseList);
        assertEquals(2, responseList.size());
    }

    //Test when there is No transactions
    @Test
    void testGetAllRewards_NoData() {

        when(repository.findAll()).thenReturn(List.of());

        assertThrows(ResourceNotFoundException.class,
                () -> service.getAllRewards());
    }
}