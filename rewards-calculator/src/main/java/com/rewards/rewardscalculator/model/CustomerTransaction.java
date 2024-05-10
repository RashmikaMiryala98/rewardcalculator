package com.rewards.rewardscalculator.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "TRANSACTION")
@AllArgsConstructor
@NoArgsConstructor
public class CustomerTransaction {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "TRANSACTION_ID")
    private Long transactionId;

    @Column(name = "CUSTOMER_ID")
    private Long customerId;

    @Column(name = "TRANSACTION_DATE")
    private Timestamp transactionDate;

    @Column(name = "AMOUNT")
    private double transactionAmount;
}