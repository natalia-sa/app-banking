package com.natalia.banking.dto.transaction;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.natalia.banking.enums.transaction.TransactionType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record TransactionRequestDto(

        @JsonProperty("forma_pagamento")
        @Schema(description = "Forma de pagamento da transação", example = "P")
        @NotNull
        TransactionType transactionType,

        @JsonProperty("numero_conta")
        @Schema(description = "Número da conta para a transação", example = "1234")
        @NotNull
        Integer accountNumber,

        @JsonProperty("valor")
        @Schema(description = "Valor da transação", example = "150.00")
        @NotNull
        @PositiveOrZero
        Double value
) {
}
