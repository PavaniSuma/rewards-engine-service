package com.rewards.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.rewards.dto.CustomerRewardsResponse;
import com.rewards.service.RewardsService;

import jakarta.validation.constraints.Min;

import java.util.List;

@RestController
@RequestMapping("/api/v1/rewards")
@Validated
public class RewardsController {

    private final RewardsService rewardsService;

    public RewardsController(RewardsService rewardsService) {
        this.rewardsService = rewardsService;
    }

    /**
     * Get reward details (monthly + total) for a single customer
     */
    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerRewardsResponse> getCustomerRewards(
            @PathVariable @Min(1) Long customerId) {

        CustomerRewardsResponse response =
                rewardsService.getRewardsByCustomer(customerId);

        return ResponseEntity.ok(response);
    }

    /**
     * Get reward details for all customers
     */
    @GetMapping
    public ResponseEntity<List<CustomerRewardsResponse>> getAllCustomersRewards() {

        List<CustomerRewardsResponse> response =
                rewardsService.getAllRewards();

        return ResponseEntity.ok(response);
    }
}