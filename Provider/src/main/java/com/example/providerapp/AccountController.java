package com.example.providerapp;

import jakarta.websocket.server.PathParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {


    public ResponseEntity<Account> createAccount(@RequestBody Account account) {

        account.setComment("Created by ProviderApp");

        return ResponseEntity.ok(account);
    }

    @GetMapping("/account/{id}")
    public ResponseEntity<Account> readAccount(@PathVariable String id) {

        Account accountExpected = new Account();



        accountExpected.setAccountId(1002);
        accountExpected.setName("John Doe");
        accountExpected.setEmail("test@test.com");
        //accountExpected.setActive(true);
        accountExpected.setOpendate("2020-01-01");


        return ResponseEntity.ok(accountExpected);
    }
}
