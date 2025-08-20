package com.expense.service.controller;


import com.expense.service.dto.ExpenseDto;
import com.expense.service.service.ExpenseService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ExpenseController {

    private final ExpenseService expenseService;

    @Autowired
    public ExpenseController (ExpenseService expenseService)
    {
        this.expenseService =expenseService;
    }

    @GetMapping("/expense/v1/")
    public ResponseEntity<List<ExpenseDto>> getExpenses(@PathVariable("user_id")
                                                            @NonNull String userId) {
        try {
            List<ExpenseDto> expenseDtoList = this.expenseService.getExpenses(userId);
            return new ResponseEntity<>(expenseDtoList, HttpStatus.OK);
        }
        catch (Exception exception)
        {
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }

    }
}
