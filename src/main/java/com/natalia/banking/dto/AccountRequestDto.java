package com.natalia.banking.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AccountRequestDto(
        @JsonProperty("numero_conta")
        Integer number,

        @JsonProperty("saldo")
        Double balance
) {
}
