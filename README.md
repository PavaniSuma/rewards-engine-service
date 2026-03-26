# rewards-engine-service

Reward calculation based on purchased records

Rewards Engine Service is a Spring Boot-based REST API that calculates customer reward points based on their transaction history.

This application calculates reward points for customers based on their transactions.

It exposes REST APIs to:

Get reward points for a single customer
Get reward points for all customers

 **Business Logic**

Reward points are calculated based on the following :

2 points for every dollar spent over $100

1 point for every dollar spent between $50 and $100

Example:

purchase = $120

120 × 2 = 40 points

50 × 1 = 50 points
 Total = 90 points
 ----------------------------------

REST API to fetch reward points

Monthly and total reward calculation per customer

Used H2 in-memory database for quick testing

 **Layered Architecture:**

Controller

Service

Repository

data loading using Schema.sql

**Tech Stack**

Java 8
Spring Boot 3.x
Spring Data JPA
H2 In-Memory Database
Maven
JUnit 5 (Mockito + Integration Tests)
--------------------------
PROJECT STRUCTURE:
src 
├── main 
│ ├── java 
│ │ └── com.rewards 
│ │  ├── controller 
│ │    │ └── RewardsController.java 
│ │  ├── service
│ │ │    ├── RewardsService.java 
│ │  │   └── RewardsServiceImpl.java 
│ │  ├── repository 
│ │  │   └── TransactionRepository.java 
│ │  ├── entity 
│ │ │    └── Transaction.java 
│ │  ├── dto 
│ │ │    ├── CustomerRewardsResponse.java 
│ │ │    └── MonthlyRewardPoints.java 
│ │  ├── exception     
         ├──ErrorResponse.java
│ │ │    ├── ResourceNotFoundException.java 
│ │ │    └── GlobalExceptionHandler.java 
│ │ └── util 
│ │     └── RewardsCalculator.java 
│ ├── resources 
│ │     ├── application.properties 
│ │     └── Schema.sql 
│  │ └── test 
         └── java 
         └── com.rewards 
             ├── RewardsController.java 
             ├── RewardsServiceImplTest.java 
             └── RewardsIntegrationTests.java

**Get Rewards for All Customers**

GET /api/v1/rewards

 Response:
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
**Get Rewards for Single Customer**

GET /api/v1/rewards/{customerId}


**Response**
{
  "customerId": 1,
  "totalPoints": 365,
  "monthlySummary": [
    {
      "month": "JANUARY",
      "totalAmountSpent": 120,
      "rewardPoints": 90
    },
    {
      "month": "FEBRUARY",
      "totalAmountSpent": 75,
      "rewardPoints": 25
    },
    {
      "month": "MARCH",
      "totalAmountSpent": 200,
      "rewardPoints": 250
    }
  ]
}

** Exception Handling**

Handled using:

**GlobalExceptionHandler**

**Custom Exception:**

ResourceNotFoundException → when customer not found


** Testing**
** Unit Tests
RewardsServiceImplTest
RewardsControllerTest

**Integration Tests**
RewardsIntegrationTest

**Database**
H2 In-Memory Database
Preloaded using schema.sql H2 Console
http://localhost:8080/h2-console

JDBC URL:

jdbc:h2:mem:testdb
