package com.practice.demo.controller;

import com.practice.demo.sevice.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class AccountController {
    private static final Logger log = LoggerFactory.getLogger(AccountController.class);

    private final UserService userService;
    @Autowired
    public AccountController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("users/{id}")
    public ResponseEntity<?> getAccountUser(@PathVariable long id){
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());

    }


}
