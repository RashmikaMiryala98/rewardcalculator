package com.rewards.rewardscalculator.service;


import com.rewards.rewardscalculator.dto.RewardsDto;
import com.rewards.rewardscalculator.dto.Transaction;
import com.rewards.rewardscalculator.model.Customer;
import com.rewards.rewardscalculator.model.CustomerTransaction;
import com.rewards.rewardscalculator.repository.CustomerRepository;
import com.rewards.rewardscalculator.repository.CustomerTransactionRepository;
import com.rewards.rewardscalculator.util.RewardCalculatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RewardsCalculatorService {


    @Autowired
    private CustomerTransactionRepository transactionRepository;
    @Autowired
    private CustomerRepository customerRepository;


    public void addCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    public RewardsDto calculateRewardsForCustomerId(Long customerId) {

        LocalDateTime now = LocalDateTime.now();
        List<CustomerTransaction> currentMonthTransaction = transactionRepository.findAllByTransactionDate(customerId, now.getYear(), now.getMonthValue());

        LocalDateTime previousMonth = now.minusMonths(1);
        List<CustomerTransaction> previousMonthTransaction = transactionRepository.findAllByTransactionDate(customerId, previousMonth.getYear(), previousMonth.getMonthValue());

        LocalDateTime previousToPreviousMonth = now.minusMonths(2);
        List<CustomerTransaction> previousToPreviousMonthTransaction = transactionRepository.findAllByTransactionDate(customerId, previousToPreviousMonth.getYear(), previousToPreviousMonth.getMonthValue());


        RewardCalculatorUtil rewardCalculatorUtil = new RewardCalculatorUtil();
        Long currentMonthRewardsPoints = rewardCalculatorUtil.calculateRewards(currentMonthTransaction);
        Long previousMonthRewardsPoints = rewardCalculatorUtil.calculateRewards(previousMonthTransaction);
        Long previousToPreviousMonthRewardsPoints = rewardCalculatorUtil.calculateRewards(previousToPreviousMonthTransaction);
        Long totalRewardsPoints = currentMonthRewardsPoints + previousMonthRewardsPoints + previousToPreviousMonthRewardsPoints;

        Map<String, Long> monthlyRewards = new HashMap<>();
        monthlyRewards.put(now.getMonth().name(), currentMonthRewardsPoints);
        monthlyRewards.put(previousMonth.getMonth().name(), previousMonthRewardsPoints);
        monthlyRewards.put(previousToPreviousMonth.getMonth().name(), previousToPreviousMonthRewardsPoints);

        return new RewardsDto(customerId, monthlyRewards, totalRewardsPoints);

    }

    public void createTransaction(Transaction transaction) {
        LocalDateTime now = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(now);

        CustomerTransaction customerTransaction = new CustomerTransaction();
        customerTransaction.setCustomerId(transaction.getCustomerId());
        customerTransaction.setTransactionAmount(transaction.getTransactionAmount());
        customerTransaction.setTransactionDate(timestamp);
        transactionRepository.save(customerTransaction);
    }
}
