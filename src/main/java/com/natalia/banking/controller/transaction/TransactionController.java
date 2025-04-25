package com.natalia.banking.controller.transaction;

import com.natalia.banking.dto.account.AccountDto;
import com.natalia.banking.dto.transaction.TransactionDto;
import com.natalia.banking.service.transaction.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/transacao")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping(value = "")
    @Operation(summary = "Realiza nova transação")
    public ResponseEntity create(
            @RequestBody
            @Valid
            TransactionDto dto) {
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
