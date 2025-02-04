package com.ganzo.delivery.authentication_server.controller;

import com.ganzo.delivery.authentication_server.dto.auth.RequestAuthentication;
import com.ganzo.delivery.authentication_server.dto.auth.ResponseAuthentication;
import com.ganzo.delivery.authentication_server.entity.User;
import com.ganzo.delivery.authentication_server.services.security.AuthAndCreateUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;

@RestController
@RequestMapping("/create")
public class CreateUserController {

    private AuthAndCreateUserService createAccount;

    @Autowired
    public CreateUserController(AuthAndCreateUserService createAccount){
        this.createAccount = createAccount;
    }

    @PostMapping("/user")
    public ResponseEntity<?> createCliente(@RequestBody User user) throws Exception {
        try {
            User createNewUser = createAccount.createAccount(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(createNewUser);

        }catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/auth")
    public ResponseEntity<?> authenticationClient(@RequestBody RequestAuthentication authentication) throws Exception {
        try {
            ResponseAuthentication verifyAuthentication = createAccount.authentication(authentication);
            return ResponseEntity.ok(authentication);
        }catch (AuthenticationException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }

    }


}
