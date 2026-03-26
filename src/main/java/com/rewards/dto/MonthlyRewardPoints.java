package com.rewards.dto;

public class MonthlyRewardPoints {
private String month;
public String getMonth() {
	return month;
}

public void setMonth(String month) {
	this.month = month;
}

public int getPoints() {
	return points;
}

public void setPoints(int points) {
	this.points = points;
}

private int points;

public MonthlyRewardPoints(String month, int points) {
    this.month = month;
    this.points = points;
}	
	
}
