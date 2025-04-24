package com.natalia.banking.controller;

import com.natalia.banking.dto.AccountDto;
import com.natalia.banking.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/conta")
public class AccountController {

    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping(value = "")
    @Operation(summary = "Create new account")
    public ResponseEntity create(
            @RequestBody
            @Valid
            AccountDto dto) {
        try {
            AccountDto savedAccount = this.accountService.save(dto);
            return new ResponseEntity<>(savedAccount, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "")
    @Operation(summary = "Get account information")
    public ResponseEntity getInfo(@RequestParam("numero_conta") Integer accountNumber) {
        try {
            Optional<AccountDto> optionalAccountDto = this.accountService.findByNumber(accountNumber);

            if(optionalAccountDto.isPresent()) {
                return new ResponseEntity<>(optionalAccountDto.get(), HttpStatus.OK);
            }

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
