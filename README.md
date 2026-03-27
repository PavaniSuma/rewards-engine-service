# Customer Rewards Service

## Overview

This project provides REST APIs to calculate reward points for customers based on their transactions.

A retailer offers a rewards program:

* 2 points for every dollar spent over $100
* 1 point for every dollar spent between $50 and $100

The system calculates:

* Monthly reward points
* Total reward points
* For last 3 months transactions

---

## Tech Stack

* Java 8
* Spring Boot 3.x
* Spring Data JPA
* H2 In-Memory Database
* Maven
* JUnit 5
* Mockito

---

## Project Structure

```text
src 
├── main 
│ ├── java 
│ │ └── com.rewards 
│ │     ├── controller 
│ │     │   └── RewardsController.java 
│ │     ├── service
│ │     │   ├── RewardsService.java 
│ │     │   └── RewardsServiceImpl.java 
│ │     ├── repository 
│ │     │   └── TransactionRepository.java 
│ │     ├── entity 
│ │     │   └── Transaction.java 
│ │     ├── dto 
│ │     │   ├── CustomerRewardsResponse.java 
│ │     │   └── MonthlyRewardPoints.java 
│ │     ├── exception 
│ │     │   ├── ResourceNotFoundException.java 
│ │     │   └── GlobalExceptionHandler.java 
│ │     └── util 
│ │         └── RewardsCalculator.java 
│ ├── resources 
│ │     ├── application.properties 
│ │     └── data.sql 
│
└── test 
    └── java 
        └── com.rewards 
            ├── RewardsControllerTest.java 
            ├── RewardsServiceImplTest.java 
            └── RewardsIntegrationTest.java
```

---

## Reward Calculation Logic

* If amount > 100 → 2 * (amount - 100)
* If amount > 50 → 1 * (amount - 50)
* Else → 0

### Example

Purchase: $120
Points = (2 × 20) + (1 × 50) = 90 points

---

## API Endpoints

### 1. Get Rewards for All Customers

**GET** `/api/v1/rewards`

#### Response

```json
[
  {
    "customerId": 1,
    "totalPoints": 365
  },
  {
    "customerId": 2,
    "totalPoints": 200
  }
]
```

---

### 2. Get Rewards by Customer

**GET** `/api/v1/rewards/{customerId}`

#### Example Request

```
/api/v1/rewards/1
```

#### Response

```json
{
  "customerId": 1,
  "monthlyPoints": [
    {
      "month": "JANUARY",
      "totalAmount": 120,
      "points": 90
    },
    {
      "month": "FEBRUARY",
      "totalAmount": 75,
      "points": 25
    },
    {
      "month": "MARCH",
      "totalAmount": 200,
      "points": 250
    }
  ],
  "totalPoints": 365
}
```

---

## Features

* Calculates rewards per transaction
* Aggregates rewards per month
* Returns total rewards per customer
* Filters last 3 months data
* Uses BigDecimal for precision
* Exception handling implemented

---

## Database

H2 In-Memory Database is used.

### Sample Table

```
transactions
-------------------------
id | customer_id | amount | date
```

---

## Exception Handling

Handled using:

* ResourceNotFoundException
* GlobalExceptionHandler

---

## Testing

* Unit Tests:

  * RewardsServiceImplTest
  * RewardsControllerTest
* Integration Test:

  * RewardsIntegrationTest

Covers:

* Success scenarios
* Invalid URL
* Customer not found
* Multiple customers
