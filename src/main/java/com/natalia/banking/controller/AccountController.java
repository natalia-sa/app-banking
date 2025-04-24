package com.natalia.banking.controller;

import com.natalia.banking.dto.AccountRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/conta")
public class AccountController {

    @PostMapping(value = "")
    @Operation(summary = "Create new account")
    public ResponseEntity create(
            @RequestBody
            @Valid
            AccountRequestDto dto) {
        try {
            System.out.println("body:: " + dto);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "")
    @Operation(summary = "Get account information")
    public ResponseEntity getInfo(@RequestParam("numero_conta") Integer accountNumber) {
        try {
            System.out.println("account number:: " + accountNumber);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
