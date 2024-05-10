package com.rewards.rewardscalculator.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
@Entity
@Table(name = "CUSTOMER")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CUSTOMER_ID")
    private Long customerId;

    @Column(name = "CUSTOMER_NAME")
    private String customerName;
}
