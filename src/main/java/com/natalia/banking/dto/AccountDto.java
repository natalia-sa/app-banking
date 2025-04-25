package com.natalia.banking.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.natalia.banking.model.Account;
import io.swagger.v3.oas.annotations.media.Schema;

public record AccountDto(
        @JsonProperty("numero_conta")
        @Schema(description = "Número da conta", example = "1234")
        Integer number,

        @JsonProperty("saldo")
        @Schema(description = "Saldo da conta", example = "100.50")
        Double balance
) {
        public AccountDto(Account savedAccount) {
                this(savedAccount.getAccountNumber(), savedAccount.getBalance());
        }
}
