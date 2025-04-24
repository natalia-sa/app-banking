package com.natalia.banking.service;

import com.natalia.banking.dto.AccountDto;
import com.natalia.banking.dto.TransactionRequestDto;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TransactionService {

    private final AccountService accountService;

    public TransactionService(AccountService accountService) {
        this.accountService = accountService;
    }

    public Optional<AccountDto> makeTransaction(TransactionRequestDto dto) {
        return accountService.updateBalance(dto.accountNumber(), dto.value());
    }
}
