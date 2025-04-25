package com.natalia.banking.service.account;


import com.natalia.banking.dto.account.AccountDto;
import com.natalia.banking.model.account.Account;
import com.natalia.banking.repository.account.AccountRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountDto save(AccountDto dto) {
        Optional<Account> existingAccount = accountRepository.findByAccountNumber(dto.number());

        if (existingAccount.isPresent()) {
            throw new IllegalArgumentException("Conta já existente");
        }

        Account account = new Account(dto.number(), dto.balance());
        Account savedAccount = accountRepository.save(account);
        return new AccountDto(savedAccount);
    }

    public Optional<AccountDto> findByNumber(Integer number) {
        return accountRepository.findByAccountNumber(number)
                .map(AccountDto::new);
    }

    @Transactional
    public Optional<AccountDto> updateBalance(Integer accountNumber, Double value) {
        return accountRepository.findByAccountNumber(accountNumber)
                .filter(account -> account.getBalance() >= value)
                .map(account -> {
                    account.setBalance(account.getBalance() - value);
                    Account updated = accountRepository.save(account);
                    return new AccountDto(updated);
                });
    }

}
