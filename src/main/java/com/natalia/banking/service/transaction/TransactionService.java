package com.natalia.banking.service.transaction;

import com.natalia.banking.dto.account.AccountDto;
import com.natalia.banking.dto.transaction.TransactionDto;
import com.natalia.banking.enums.transaction.TransactionType;
import com.natalia.banking.service.account.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final AccountService accountService;

    public Optional<AccountDto> makeTransaction(TransactionDto dto) {
        double total = calculateValueWithFee(dto.value(), dto.transactionType());
        return accountService.updateBalance(dto.accountNumber(), total);
    }

    private double calculateValueWithFee(double value, TransactionType type) {
        return switch (type) {
            case D -> value * 1.03;
            case C -> value * 1.05;
            case P -> value;
        };
    }
}
