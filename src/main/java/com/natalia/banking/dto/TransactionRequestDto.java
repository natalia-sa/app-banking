package com.natalia.banking.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TransactionRequestDto(

        @JsonProperty("forma_pagamento")
        String paymentType,


        @JsonProperty("numero_conta")
        Integer accountNumber,


        @JsonProperty("valor")
        Double value
) {
}
