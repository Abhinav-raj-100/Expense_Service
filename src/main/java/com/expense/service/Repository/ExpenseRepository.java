package com.expense.service.Repository;

import com.expense.service.Entities.Expense;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;


@Repository
public interface ExpenseRepository extends CrudRepository<Expense, Long> {

    List<Expense> findByUserId(String userId);

    // FIXED: changed `created` → `createdAt`, and made both params Timestamp
    List<Expense> findByUserIdAndCreatedAtBetween(String userId, Timestamp startTime, Timestamp endTime);

    Optional<Expense> findByUserIdAndExternalId(String userId, String externalId);
}


