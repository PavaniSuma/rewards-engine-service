package com.rewards.service;

import com.rewards.dto.CustomerRewardsResponse;
import com.rewards.dto.MonthlyRewardPoints;
import com.rewards.entity.Transaction;
import com.rewards.exceptions.ResourceNotFoundException;
import com.rewards.repository.TransactionRepository;
import com.rewards.util.RewardsCalculator;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RewardsServiceImpl implements RewardsService {

    private final TransactionRepository repository;
    private final RewardsCalculator calculator;

 
    public RewardsServiceImpl(TransactionRepository repository,
                              RewardsCalculator calculator) {
        this.repository = repository;
        this.calculator = calculator;
    }

    /**
     *  Get rewards for a customer with customerID
     */
    @Override
    public CustomerRewardsResponse getRewardsByCustomer(Long customerId) {

        List<Transaction> transactions =
                repository.findByCustomerId(customerId);

        if (transactions.isEmpty()) {
            throw new ResourceNotFoundException(
                    "No transactions found for customer " + customerId);
        }

        return calculateRewardsForCustomer(customerId, transactions);
    }

    /**
     *Get rewards for all customers
     */
    @Override
    public List<CustomerRewardsResponse> getAllRewards() {

        List<Transaction> allTransactions = repository.findAll();

        if (allTransactions.isEmpty()) {
            throw new ResourceNotFoundException("No transactions found");
        }

        //Group by customerId
        Map<Long, List<Transaction>> transactionsByCustomer =
                allTransactions.stream()
                        .collect(Collectors.groupingBy(Transaction::getCustomerId));

        //Calculating rewards for each customer
        return transactionsByCustomer.entrySet().stream()
                .map(entry -> calculateRewardsForCustomer(
                        entry.getKey(),
                        entry.getValue()
                ))
                .collect(Collectors.toList());
    }

   
    private CustomerRewardsResponse calculateRewardsForCustomer(
            Long customerId,
            List<Transaction> transactions) {

       
        Map<Month, List<Transaction>> groupedByMonth =
                transactions.stream()
                        .collect(Collectors.groupingBy(
                                t -> t.getDate().getMonth()
                        ));

        List<MonthlyRewardPoints> monthlyList = new ArrayList<>();
        int totalPoints = 0;

        // Loop each month
        for (Map.Entry<Month, List<Transaction>> entry : groupedByMonth.entrySet()) {

            Month month = entry.getKey();
            List<Transaction> monthlyTransactions = entry.getValue();

            // Total amount per month
            
            BigDecimal totalAmount = monthlyTransactions.stream()
                    .map(Transaction::getAmount)
                    .reduce(BigDecimal.ZERO,
                    		BigDecimal :: add);
       

            // Reward points per month
            int monthlyPoints = monthlyTransactions.stream()
                    .mapToInt(t -> calculator.calculate(t.getAmount()))
                    .sum();

            totalPoints += monthlyPoints;

            monthlyList.add(
                    new MonthlyRewardPoints(month, totalAmount, monthlyPoints));
        }

        return new CustomerRewardsResponse(
                customerId,
                monthlyList,
                totalPoints
        );
    }
}