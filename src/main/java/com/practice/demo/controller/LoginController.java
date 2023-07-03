package com.practice.demo.controller;

import com.practice.demo.dto.AccountDto;
import com.practice.demo.dto.LoginDto;
import com.practice.demo.sevice.AccountMyBatisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api")
public class LoginController {
    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    private final AccountMyBatisService accountMyBatisService;
    @Autowired
    public LoginController(AccountMyBatisService accountMyBatisService) {
        this.accountMyBatisService = accountMyBatisService;
    }

    @PostMapping("login")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto){
        log.debug("check login:{}",loginDto.getAccount());
        return accountMyBatisService.login(loginDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("user login fail"));

    }
}
