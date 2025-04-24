package com.natalia.banking.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.natalia.banking.model.Account;

public record AccountDto(
        @JsonProperty("numero_conta")
        Integer number,

        @JsonProperty("saldo")
        Double balance
) {
        public AccountDto(Account savedAccount) {
                this(savedAccount.getAccountNumber(), savedAccount.getBalance());
        }
}
