package com.natalia.banking.controller.account;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.natalia.banking.dto.account.AccountDto;
import com.natalia.banking.service.account.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AccountController.class)
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AccountService accountService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateAccount() throws Exception {
        AccountDto requestDto = new AccountDto(123, 500.0);
        when(accountService.save(any())).thenReturn(requestDto);

        mockMvc.perform(post("/conta")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.numero_conta").value(123))
                .andExpect(jsonPath("$.saldo").value(500.0));
    }

    @Test
    void shouldReturnServerErrorWhenExceptionThrown() throws Exception {
        AccountDto requestDto = new AccountDto(123, 500.0);
        when(accountService.save(any())).thenThrow(new RuntimeException("Something went wrong"));

        mockMvc.perform(post("/conta")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void shouldGetAccount() throws Exception {
        AccountDto responseDto = new AccountDto(456, 300.0);
        when(accountService.findByNumber(456)).thenReturn(Optional.of(responseDto));

        mockMvc.perform(get("/conta")
                        .param("numero_conta", "456"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numero_conta").value(456))
                .andExpect(jsonPath("$.saldo").value(300.0));
    }

    @Test
    void shouldReturnNotFoundWhenAccountDoesNotExist() throws Exception {
        when(accountService.findByNumber(999)).thenReturn(Optional.empty());

        mockMvc.perform(get("/conta")
                        .param("numero_conta", "999"))
                .andExpect(status().isNotFound());
    }

}
