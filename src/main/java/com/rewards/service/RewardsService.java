package com.rewards.service;

import com.rewards.dto.CustomerRewardsResponse;

import java.util.List;

public interface RewardsService {

    CustomerRewardsResponse getRewardsByCustomer(Long customerId);

    List<CustomerRewardsResponse> getAllRewards();
}
