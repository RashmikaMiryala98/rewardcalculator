package com.rewards.rewardscalculator.service;

import com.rewards.rewardscalculator.dto.RewardsDto;
import com.rewards.rewardscalculator.dto.Transaction;
import com.rewards.rewardscalculator.model.Customer;
import com.rewards.rewardscalculator.model.CustomerTransaction;
import com.rewards.rewardscalculator.repository.CustomerRepository;
import com.rewards.rewardscalculator.repository.CustomerTransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class RewardsCalculatorServiceTest {

    @InjectMocks
    RewardsCalculatorService rewardsCalculatorService;
    @Mock
    CustomerTransactionRepository customerTransactionRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Test
    public void test_calculateRewards() {

        List<CustomerTransaction> transactionList = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();


        CustomerTransaction t1 = new CustomerTransaction();
        t1.setCustomerId(1L);
        t1.setTransactionAmount(120);
        t1.setTransactionId(1L);
        t1.setTransactionDate(Timestamp.valueOf(now));

        transactionList.add(t1);

        when(customerTransactionRepository.findAllByTransactionDate(anyLong(), anyInt(), anyInt())).thenReturn(transactionList);

        RewardsDto rewards = rewardsCalculatorService.calculateRewardsForCustomerId(1L);
        assertThat(rewards).isNotNull();
        assertThat(rewards.getTotalRewards()).isEqualTo(270L);

    }

    @Test
    public void test_createTransaction() {
        // Create a sample transaction
        Transaction transaction = new Transaction();
        transaction.setCustomerId(1L);
        transaction.setTransactionAmount(120.0);

        // Call the method under test
        rewardsCalculatorService.createTransaction(transaction);

        // Verify that the save method of transactionRepository was called once with the expected CustomerTransaction object
        verify(customerTransactionRepository, times(1)).save(Mockito.any(CustomerTransaction.class));
    }

    @Test
    public void testAddCustomer() {
        // Create a sample customer
        Customer customer = new Customer();
        customer.setCustomerId(1L);
        customer.setCustomerName("Thomas Anderson");

        // Call the method under test
        rewardsCalculatorService.addCustomer(customer);

        // Verify that the save method of customerRepository was called once with the expected Customer object
        verify(customerRepository, times(1)).save(customer);
    }
}