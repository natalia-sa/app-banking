package com.natalia.banking.controller;

import com.natalia.banking.dto.AccountDto;
import com.natalia.banking.dto.TransactionRequestDto;
import com.natalia.banking.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/transacao")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping(value = "")
    @Operation(summary = "Make new transaction")
    public ResponseEntity create(
            @RequestBody
            @Valid
            TransactionRequestDto dto) {
        try {
            Optional<AccountDto> optionalAccountDto = transactionService.makeTransaction(dto);
            if(optionalAccountDto.isPresent()) {
                return new ResponseEntity<>(optionalAccountDto, HttpStatus.CREATED);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
