package com.ganzo.delivery.authentication_server.controller;

import com.ganzo.delivery.authentication_server.dto.auth.RequestAuthentication;
import com.ganzo.delivery.authentication_server.dto.auth.ResponseAuthentication;
import com.ganzo.delivery.authentication_server.dto.response.ResponseGeneric;
import com.ganzo.delivery.authentication_server.entity.User;
import com.ganzo.delivery.authentication_server.services.security.AuthAndCreateUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;

@RestController
@RequestMapping("/user")
public class UserController {

    private AuthAndCreateUser createAccount;

    @Autowired
    public UserController(AuthAndCreateUser createAccount){
        this.createAccount = createAccount;
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseGeneric> createClient(@RequestBody User user) throws Exception {
        try {
            User createNewUser = createAccount.createAccount(user);
            ResponseGeneric<User> responseGeneric = new ResponseGeneric<>();
            responseGeneric.setData(createNewUser);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseGeneric);

        } catch (IllegalArgumentException e){
            ResponseGeneric<String> responseGeneric = new ResponseGeneric<>();
            responseGeneric.setData(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseGeneric);
        }
    }

    @PostMapping("/auth")
    public ResponseEntity<ResponseGeneric> authenticationClient(@RequestBody RequestAuthentication authentication) throws Exception {
        try {
            ResponseAuthentication verifyAuthentication = createAccount.authentication(authentication);
            ResponseGeneric<ResponseAuthentication> responseGeneric = new ResponseGeneric<>();
            responseGeneric.setData(verifyAuthentication);
            return ResponseEntity.ok(responseGeneric);
        }catch (AuthenticationException e){
            ResponseGeneric<String> responseGeneric = new ResponseGeneric<>();
            responseGeneric.setData(e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseGeneric);
        }

    }


}
