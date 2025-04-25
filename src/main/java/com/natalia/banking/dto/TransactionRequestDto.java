package com.natalia.banking.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.natalia.banking.enums.TransactionType;

public record TransactionRequestDto(

        @JsonProperty("forma_pagamento")
        TransactionType transactionType,


        @JsonProperty("numero_conta")
        Integer accountNumber,


        @JsonProperty("valor")
        Double value
) {
}
