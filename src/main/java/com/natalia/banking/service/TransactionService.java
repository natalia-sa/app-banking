package com.natalia.banking.service;

import com.natalia.banking.dto.AccountDto;
import com.natalia.banking.dto.TransactionRequestDto;
import com.natalia.banking.enums.TransactionType;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TransactionService {

    private final AccountService accountService;

    public TransactionService(AccountService accountService) {
        this.accountService = accountService;
    }

    public Optional<AccountDto> makeTransaction(TransactionRequestDto dto) {
        double total = calculateValueWithFee(dto.value(), dto.transactionType());
        return accountService.updateBalance(dto.accountNumber(), total);
    }

    private double calculateValueWithFee(double value, TransactionType type) {
        switch (type) {
            case D:
                return value * 1.03;
            case C:
                return value * 1.05;
            case P:
                return value;
            default:
                throw new IllegalArgumentException("Invalid transaction type");
        }
    }
}
