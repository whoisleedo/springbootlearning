package com.practice.demo.controller;


import com.practice.demo.dto.AccessToken;
import com.practice.demo.dto.CommonResponse;
import com.practice.demo.dto.LoginDto;
import com.practice.demo.dto.StatusCode;
import com.practice.demo.sevice.AccountMyBatisService;
import com.practice.demo.util.ValidateUtil;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Operation(summary = "login api", description = "login api")
    @PostMapping("sessions")
    public ResponseEntity<CommonResponse<AccessToken>> login(@RequestBody LoginDto loginDto){
        log.debug("check login:{}",loginDto.getAccount());
        if(!isLoginDataCorrect(loginDto)){
            log.debug("login data invalid account:{}", loginDto.getAccount());
            return ResponseEntity.badRequest().body(generateInvalidResponse());
        }

        return accountMyBatisService.login(loginDto)
                .map(AccessToken::new)
                .map(this::generateSuccessResponse)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity
                    .badRequest().body(generateInvalidResponse()));

    }

    private boolean isLoginDataCorrect(LoginDto loginDto){
        return ValidateUtil.isAccountCorrect(loginDto.getAccount()) &&
                ValidateUtil.isPasswordCorrect(loginDto.getPassword());
    }

    private CommonResponse<AccessToken> generateInvalidResponse(){
        return new CommonResponse<>(StatusCode.InvalidData.getValue(),
                "account or password is incorrect");
    }

    private CommonResponse<AccessToken> generateSuccessResponse(AccessToken token){
        return new CommonResponse<>(StatusCode.OK.getValue(),"success",token);
    }
}
