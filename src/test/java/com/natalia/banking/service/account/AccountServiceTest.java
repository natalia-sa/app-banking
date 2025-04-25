package com.natalia.banking.service.account;

import com.natalia.banking.dto.account.AccountDto;
import com.natalia.banking.model.account.Account;
import com.natalia.banking.repository.account.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.never;

class AccountServiceTest {

    private AccountRepository accountRepository;
    private AccountService accountService;

    @BeforeEach
    void setUp() {
        accountRepository = mock(AccountRepository.class);
        accountService = new AccountService(accountRepository);
    }

    @Test
    void shouldSaveNewAccount() {
        Account account = new Account(123, 200.0);
        AccountDto dto = new AccountDto(account);

        when(accountRepository.save(any())).thenReturn(account);

        AccountDto result = accountService.save(dto);

        assertThat(result.number()).isEqualTo(123);
        assertThat(result.balance()).isEqualTo(200.0);

        verify(accountRepository).save(any(Account.class));
    }

    @Test
    void shouldNotSaveWhenAccountAlreadyExists() {
        Account account = new Account(123, 200.0);
        AccountDto dto = new AccountDto(account);

        when(accountRepository.findByAccountNumber(123)).thenReturn(Optional.of(account));

        assertThrows(IllegalArgumentException.class, () -> {
            accountService.save(dto);
        });

        verify(accountRepository, never()).save(any());
    }

    @Test
    void shouldFindAccountByNumber() {
        Account account = new Account(123, 150.0);
        when(accountRepository.findByAccountNumber(123)).thenReturn(Optional.of(account));

        Optional<AccountDto> result = accountService.findByNumber(123);

        assertThat(result).isPresent();
        assertThat(result.get().balance()).isEqualTo(150.0);
    }

    @Test
    void shouldUpdateBalance() {
        Account account = new Account(123, 200.0);
        when(accountRepository.findByAccountNumber(123)).thenReturn(Optional.of(account));
        when(accountRepository.save(any())).thenReturn(account);

        Optional<AccountDto> result = accountService.updateBalance(123, 50.0);

        assertThat(result).isPresent();
        assertThat(result.get().balance()).isEqualTo(150.0);

        verify(accountRepository).save(any(Account.class));
    }

    @Test
    void shouldNotUpdateBalanceWhenInsufficientBalance() {
        Account account = new Account(123, 40.0);
        when(accountRepository.findByAccountNumber(123)).thenReturn(Optional.of(account));

        Optional<AccountDto> result = accountService.updateBalance(123, 50.0);

        assertThat(result).isEmpty();
        verify(accountRepository, never()).save(any());
    }
}
