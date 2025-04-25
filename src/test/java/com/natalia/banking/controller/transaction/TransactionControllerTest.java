package com.natalia.banking.controller.transaction;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.natalia.banking.dto.account.AccountDto;
import com.natalia.banking.dto.transaction.TransactionRequestDto;
import com.natalia.banking.enums.transaction.TransactionType;
import com.natalia.banking.service.transaction.TransactionService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TransactionController.class)
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TransactionService transactionService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldMakeTransaction() throws Exception {
        TransactionRequestDto requestDto = new TransactionRequestDto(
                TransactionType.P, 123, 100.0
        );

        AccountDto resultDto = new AccountDto(123, 400.0);
        Mockito.when(transactionService.makeTransaction(Mockito.any()))
                .thenReturn(Optional.of(resultDto));

        mockMvc.perform(post("/transacao")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.numero_conta").value(123))
                .andExpect(jsonPath("$.saldo").value(400.0));
    }

    @Test
    void shouldReturnNotFoundWhenTransactionFails() throws Exception {
        TransactionRequestDto requestDto = new TransactionRequestDto(
                TransactionType.C, 456, 500.0
        );

        Mockito.when(transactionService.makeTransaction(Mockito.any()))
                .thenReturn(Optional.empty());

        mockMvc.perform(post("/transacao")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnInternalServerErrorWhenExceptionThrown() throws Exception {
        TransactionRequestDto requestDto = new TransactionRequestDto(
                TransactionType.D, 789, 1000.0
        );

        Mockito.when(transactionService.makeTransaction(Mockito.any()))
                .thenThrow(new RuntimeException("Unexpected error"));

        mockMvc.perform(post("/transacao")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void shouldReturnBadRequestWhenTransactionTypeIsnull() throws Exception {
        TransactionRequestDto requestDto = new TransactionRequestDto(
                null, 789, 1000.0
        );

        mockMvc.perform(post("/transacao")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestWhenAccountNumberIsnull() throws Exception {
        TransactionRequestDto requestDto = new TransactionRequestDto(
                TransactionType.C, null, 1000.0
        );

        mockMvc.perform(post("/transacao")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestWhenValueIsnull() throws Exception {
        TransactionRequestDto requestDto = new TransactionRequestDto(
                TransactionType.C, 123, null
        );

        mockMvc.perform(post("/transacao")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestWhenValueIsNegative() throws Exception {
        TransactionRequestDto requestDto = new TransactionRequestDto(
                TransactionType.C, 123, -120.0
        );

        mockMvc.perform(post("/transacao")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isBadRequest());
    }
}
