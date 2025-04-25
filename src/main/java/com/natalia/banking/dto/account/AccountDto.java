package com.natalia.banking.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.natalia.banking.model.account.Account;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record AccountDto(
        @JsonProperty("numero_conta")
        @Schema(description = "Número da conta", example = "1234")
        @NotNull
        Integer number,

        @JsonProperty("saldo")
        @Schema(description = "Saldo da conta", example = "100.50")
        @NotNull
        @PositiveOrZero
        Double balance
) {
        public AccountDto(Account savedAccount) {
                this(savedAccount.getAccountNumber(), savedAccount.getBalance());
        }
}
