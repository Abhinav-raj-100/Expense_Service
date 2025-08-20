package com.expense.service.service;

import com.expense.service.Entities.Expense;
import com.expense.service.Repository.ExpenseRepository;
import com.expense.service.dto.ExpenseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ExpenseService {

    private ExpenseRepository expenseRepository;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    public boolean createExpense(ExpenseDto expenseDto)
    {
        setCurrency(expenseDto);
        try
        {
            expenseRepository.save(objectMapper.convertValue(expenseDto, Expense.class));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void setCurrency(ExpenseDto expenseDto)
    {
        if(Objects.isNull(expenseDto.getCurrency()))
        {
            expenseDto.setCurrency("INR");
        }
    }

    public boolean updateExpense(ExpenseDto expenseDto)
    {
        Optional<Expense> optionalExpense =  this.expenseRepository.findByUserIdAndExternalId(expenseDto.getUserId(),
                expenseDto.getExteranlId());

        if(optionalExpense.isEmpty())
        {
            return false;
        }

        Expense expense =  optionalExpense.get();
        expense.setCurrency(Strings.isNotBlank(expenseDto.getCurrency())
                ?expenseDto.getCurrency():expense.getCurrency());
        expense.setMerchant(Strings.isNotBlank(expenseDto.getMerchant())?expenseDto.getMerchant()
                :expense.getMerchant());
        expense.setAmount(expenseDto.getAmount());
        this.expenseRepository.save(expense);
        return true;
    }


    public List<ExpenseDto> getExpenses(String userId) {
        List<Expense> expenseList = expenseRepository.findByUserId(userId);

        return expenseList.stream()
                .map(expense -> objectMapper.convertValue(expense, ExpenseDto.class))
                .toList();
    }

}
