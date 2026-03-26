package com.rewards.util;

import org.springframework.stereotype.Component;

import com.rewards.constants.Constants;

import java.math.BigDecimal;

@Component
public class RewardsCalculator {

	public int calculate(BigDecimal amount) {

	    if (amount == null || amount.compareTo(BigDecimal.ZERO) < 0) {
	        throw new IllegalArgumentException("Invalid amount");
	    }

	    int points = 0;

	    // RewardPoints calculation above 100
	    if (amount.compareTo(Constants.UPPER_LIMIT) > 0) {
	        points += amount.subtract(Constants.UPPER_LIMIT)
	                .multiply(BigDecimal.valueOf(Constants.TWO_POINTS))
	                .intValue();

	        amount = Constants.UPPER_LIMIT;
	    }

	    // RewardPoints calculation between 50 and 100
	    if (amount.compareTo(Constants.LOWER_LIMIT) > 0) {
	        points += amount.subtract(Constants.LOWER_LIMIT)
	                .multiply(BigDecimal.valueOf(Constants.ONE_POINT))
	                .intValue();
	    }

	    return points;
	}
}