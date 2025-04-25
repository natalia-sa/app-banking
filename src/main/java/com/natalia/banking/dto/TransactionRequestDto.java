package com.natalia.banking.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.natalia.banking.enums.TransactionType;
import io.swagger.v3.oas.annotations.media.Schema;

public record TransactionRequestDto(

        @JsonProperty("forma_pagamento")
        @Schema(description = "Forma de pagamento da transação", example = "P")
        TransactionType transactionType,

        @JsonProperty("numero_conta")
        @Schema(description = "Número da conta para a transação", example = "1234")
        Integer accountNumber,

        @JsonProperty("valor")
        @Schema(description = "Valor da transação", example = "150.00")
        Double value
) {
}
