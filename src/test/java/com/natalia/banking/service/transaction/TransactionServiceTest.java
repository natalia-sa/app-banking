package com.natalia.banking.service.transaction;

import com.natalia.banking.dto.account.AccountDto;
import com.natalia.banking.dto.transaction.TransactionRequestDto;
import com.natalia.banking.enums.transaction.TransactionType;
import com.natalia.banking.model.account.Account;
import com.natalia.banking.repository.account.AccountRepository;
import com.natalia.banking.service.account.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TransactionServiceTest {
    private AccountRepository accountRepository;
    private TransactionService transactionService;

    @BeforeEach
    void setUp() {
        accountRepository = mock(AccountRepository.class);
        AccountService accountService = new AccountService(accountRepository);
        transactionService = new TransactionService(accountService);
    }

    @Test
    void shouldMakeTransactionDebit() {
        Account account = new Account(1, 200.0);
        when(accountRepository.findByAccountNumber(1)).thenReturn(Optional.of(account));
        when(accountRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        TransactionRequestDto dto = new TransactionRequestDto(TransactionType.D, 1, 100.0);

        Optional<AccountDto> result = transactionService.makeTransaction(dto);

        assertThat(result).isPresent();
        assertThat(result.get().balance()).isEqualTo(200.0 - 100.0 * 1.03);
    }

    @Test
    void shouldMakeTransactionCredit() {
        Account account = new Account(1, 500.0);
        when(accountRepository.findByAccountNumber(1)).thenReturn(Optional.of(account));
        when(accountRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        TransactionRequestDto dto = new TransactionRequestDto(TransactionType.C, 1, 200.0);

        Optional<AccountDto> result = transactionService.makeTransaction(dto);

        assertThat(result).isPresent();
        assertThat(result.get().balance()).isEqualTo(500.0 - 200.0 * 1.05);
    }

    @Test
    void shouldMakeTransactionPix() {
        Account account = new Account(1, 150.0);
        when(accountRepository.findByAccountNumber(1)).thenReturn(Optional.of(account));
        when(accountRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        TransactionRequestDto dto = new TransactionRequestDto(TransactionType.P, 1, 50.0);

        Optional<AccountDto> result = transactionService.makeTransaction(dto);

        assertThat(result).isPresent();
        assertThat(result.get().balance()).isEqualTo(100.0); // 150 - 50
    }

    @Test
    void shouldNotMakeTransactionWhenInsufficientBalance() {
        Account account = new Account(1, 20.0);
        when(accountRepository.findByAccountNumber(1)).thenReturn(Optional.of(account));

        TransactionRequestDto dto = new TransactionRequestDto(TransactionType.C, 1, 50.0);

        Optional<AccountDto> result = transactionService.makeTransaction(dto);

        assertThat(result).isEmpty();
        verify(accountRepository, never()).save(any());
    }
}
