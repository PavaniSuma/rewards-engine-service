package com.rewards.service;

import org.springframework.stereotype.Service;

import com.rewards.dto.CustomerRewardsResponse;
import com.rewards.dto.MonthlyRewardPoints;
import com.rewards.entity.Transaction;
import com.rewards.exceptions.ResourceNotFoundException;
import com.rewards.repository.TransactionRepository;
import com.rewards.util.RewardsCalculator;

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
     * Get rewards for a single customer
     */
    @Override
    public CustomerRewardsResponse getRewardsByCustomer(Long customerId) {

        List<Transaction> transactions = repository.findByCustomerId(customerId);

        if (transactions.isEmpty()) {
            throw new ResourceNotFoundException(
                    "No transactions found for customer " + customerId);
        }

        // Group transactions by Month
        
        Map<Month, List<Transaction>> groupedByMonth =
                        transactions.stream()
                                .collect(Collectors.groupingBy(
                                        t -> t.getDate().getMonth(),
                                        TreeMap :: new, Collectors.toList()
                                ));

        List<MonthlyRewardPoints> monthlyList = new ArrayList<>();
        int totalPoints = 0;

        for (Map.Entry<Month, List<Transaction>> entry : groupedByMonth.entrySet()) {

            Month month = entry.getKey();
            List<Transaction> txnList = entry.getValue();

            int monthlyPoints = 0;

            for (Transaction t : txnList) {
                monthlyPoints += calculator.calculate(t.getAmount());
            }

            totalPoints += monthlyPoints;

            monthlyList.add(
                    new MonthlyRewardPoints(month.name(), monthlyPoints)
            );
        }

        return new CustomerRewardsResponse(customerId, monthlyList, totalPoints);
    }

    /**
     * Get rewards for all customers
     */
    @Override
    public List<CustomerRewardsResponse> getAllRewards() {

        List<Transaction> transactions = repository.findAll();

        if (transactions.isEmpty()) {
            throw new ResourceNotFoundException("No transactions available");
        }

        // Group by customerId
        Map<Long, List<Transaction>> groupedByCustomer =
                transactions.stream()
                        .collect(Collectors.groupingBy(Transaction::getCustomerId));

        List<CustomerRewardsResponse> responseList = new ArrayList<>();

        for (Map.Entry<Long, List<Transaction>> customerEntry : groupedByCustomer.entrySet()) {

            Long customerId = customerEntry.getKey();
            List<Transaction> customerTransactions = customerEntry.getValue();

            // Group by Month (sorted)
            Map<Month, List<Transaction>> groupedByMonth =
                    new TreeMap<>(
                            customerTransactions.stream()
                                    .collect(Collectors.groupingBy(
                                            t -> t.getDate().getMonth()
                                    ))
                    );

            List<MonthlyRewardPoints> monthlyList = new ArrayList<>();
            int totalPoints = 0;

            for (Map.Entry<Month, List<Transaction>> entry : groupedByMonth.entrySet()) {

                Month month = entry.getKey();
                List<Transaction> txnList = entry.getValue();

                int monthlyPoints = 0;

                for (Transaction t : txnList) {
                    monthlyPoints += calculator.calculate(t.getAmount());
                }

                totalPoints += monthlyPoints;

                monthlyList.add(
                        new MonthlyRewardPoints(month.name(), monthlyPoints)
                );
            }

            responseList.add(
                    new CustomerRewardsResponse(customerId, monthlyList, totalPoints)
            );
        }

        return responseList;
    }
}