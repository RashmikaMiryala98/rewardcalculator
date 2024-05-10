package com.rewards.rewardscalculator.model;

import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;

class CustomerTransactionTest {

    @Test
    public void test_gettersAndSetters() {
        // Create a CustomerTransaction object
        CustomerTransaction transaction = new CustomerTransaction();

        // Set values using setter methods
        transaction.setTransactionId(1L);
        transaction.setCustomerId(1L);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        transaction.setTransactionDate(timestamp);
        transaction.setTransactionAmount(120.0);

        // Verify values using getter methods
        assertEquals(1L, transaction.getTransactionId());
        assertEquals(1L, transaction.getCustomerId());
        assertEquals(timestamp, transaction.getTransactionDate());
        assertEquals(120.0, transaction.getTransactionAmount());
    }

}