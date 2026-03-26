package com.rewards.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Entity is about transaction details.
 * 
 * Stores customer purchase data used for reward calculation.
 */

@Entity
@Table(name = "transactions")
public class Transaction {
	
	 public Transaction() {}

    public Transaction(Long id, Long customerId, BigDecimal amount, LocalDate date) {
		super();
		this.id = id;
		this.customerId = customerId;
		this.amount = amount;
		this.date = date;
	}
    
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer_id")
    private Long customerId;
	 public Long getId() {
			return id;
		}
	 
    public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	private BigDecimal amount;
	
    public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	private LocalDate date;

}