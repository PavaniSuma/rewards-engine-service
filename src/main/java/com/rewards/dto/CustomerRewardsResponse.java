package com.rewards.dto;

import java.util.List;

public class CustomerRewardsResponse {

    private Long customerId;
    private List<MonthlyRewardPoints> monthlyPoints;
    private int totalPoints;

    public CustomerRewardsResponse(Long customerId,
    		List<MonthlyRewardPoints> monthlyPoints,
                                   int totalPoints) {
        this.customerId = customerId;
        this.monthlyPoints = monthlyPoints;
        this.totalPoints = totalPoints;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public List<MonthlyRewardPoints> getMonthlyPoints() {
        return monthlyPoints;
    }

    public int getTotalPoints() {
        return totalPoints;
	}

}