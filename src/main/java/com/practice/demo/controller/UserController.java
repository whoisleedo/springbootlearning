package com.practice.demo.controller;


import com.practice.demo.sevice.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
    @Operation(summary = "find user by id", description = "find user by id")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("users/{id}")
    public ResponseEntity<?> getAccountUser(@PathVariable long id , Authentication authentication){
        String loginUserAccount =  (String)authentication.getPrincipal();
        log.debug("user Account:{} query userId:{}" ,loginUserAccount,id);
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());

    }



}
