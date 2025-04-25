package com.natalia.banking.controller.account;

import com.natalia.banking.dto.account.AccountDto;
import com.natalia.banking.service.account.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/conta")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

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
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "")
    @Operation(summary = "Get account information")
    public ResponseEntity getInfo(
            @Parameter(description = "Número da conta", example = "1234")
            @RequestParam("numero_conta") Integer accountNumber) {
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
