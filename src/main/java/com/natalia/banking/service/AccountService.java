package com.natalia.banking.service;


import com.natalia.banking.dto.AccountDto;
import com.natalia.banking.model.Account;
import com.natalia.banking.repository.AccountRepository;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public AccountDto save(AccountDto dto) {
        Account account = new Account(dto.number(), dto.balance());
        Account savedAccount = accountRepository.save(account);
        return new AccountDto(savedAccount);
    }

}
