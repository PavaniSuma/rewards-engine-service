package com.rewards.dto;

import java.math.BigDecimal;
import java.time.Month;

public class MonthlyRewardPoints {
	
private Month month;
public Month getMonth() {
	return month;
}


public void setMonth(Month month) {
	this.month = month;
}


public BigDecimal getTotalAmount() {
	return totalAmount;
}


public void setTotalAmount(BigDecimal totalAmount) {
	this.totalAmount = totalAmount;
}


public int getPoints() {
	return points;
}


public void setPoints(int points) {
	this.points = points;
}


private BigDecimal totalAmount;
private int points;


public MonthlyRewardPoints(Month month, BigDecimal totalAmount, int points) {
    this.month = month;
    this.totalAmount = totalAmount;
    this.points = points;
}	
	
}
