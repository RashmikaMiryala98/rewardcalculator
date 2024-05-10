package com.rewards.rewardscalculator.repository;

import com.rewards.rewardscalculator.model.Customer;
import com.rewards.rewardscalculator.model.CustomerTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    @Query("SELECT T FROM CustomerTransaction T WHERE customerId = :customerId and year(T.transactionDate) = :year AND month(T.transactionDate) = :month")
    List<CustomerTransaction> findAllTransactionsByDate(@Param("customerId") Long customerId, @Param("year") Integer year, @Param("month") Integer month);

}
