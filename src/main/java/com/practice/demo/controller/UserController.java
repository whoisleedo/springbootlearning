package com.practice.demo.controller;


import com.practice.demo.sevice.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("users/{id}")
    public ResponseEntity<?> getAccountUser(@PathVariable long id , Authentication authentication){
        String loginUserAccount =  (String)authentication.getPrincipal();
        log.debug("user Account:{} query userId:{}" ,loginUserAccount,id);
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());

    }



}
