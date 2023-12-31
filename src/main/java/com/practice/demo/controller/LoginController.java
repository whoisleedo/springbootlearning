package com.practice.demo.controller;


import com.practice.demo.dto.AccessTokenDto;
import com.practice.demo.dto.common.CommonResponse;
import com.practice.demo.dto.LoginDto;
import com.practice.demo.dto.common.StatusCode;
import com.practice.demo.sevice.AccountMyBatisService;
import com.practice.demo.util.ValidateUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api")
@Tag(name = "login api", description = "API for user login ")
public class LoginController {
    private static final Logger log = LoggerFactory.getLogger(LoginController.class);
    private final AccountMyBatisService accountMyBatisService;
    @Autowired
    public LoginController(AccountMyBatisService accountMyBatisService) {
        this.accountMyBatisService = accountMyBatisService;
    }
    @Operation(summary = "login api", description = "login api",
            requestBody =
            @io.swagger.v3.oas.annotations.parameters.RequestBody(content =
            @Content(mediaType = "application/json",
                    examples = @ExampleObject(name = "request",
                            value = "{\n" +
                                    "    \"account\":\"test2\",\n" +
                                    "    \"password\":\"password\"\n" +
                                    "}"))),
            responses = {
                    @ApiResponse(responseCode = "200", description = "login success",
                            content = @Content(mediaType = "application/json",
                                    examples = @ExampleObject(name = "success",
                                            value = "{\n" +
                                                    "    \"status\": 1,\n" +
                                                    "    \"errorMessage\": \"success\",\n" +
                                                    "    \"body\": {\n" +
                                                    "    \"accessToken\": \"jwtToken\"\n" +
                                                    "    }\n" +
                                                    "}"))

                    ),
                    @ApiResponse(responseCode = "400", description = "login fail",
                            content = @Content(mediaType = "application/json",
                                    examples = @ExampleObject(name = "loginFail",
                                            value = "{\n" +
                                                    "    \"status\": -1,\n" +
                                                    "    \"errorMessage\": \"account or password is incorrect\",\n" +
                                                    "    \"body\": null\n" +
                                                    "}"))

                    )
            }
    )
    @PostMapping("sessions")
    public ResponseEntity<CommonResponse<AccessTokenDto>> login(@RequestBody LoginDto loginDto){
        log.debug("check login:{}",loginDto.getAccount());
        if(!isLoginDataCorrect(loginDto)){
            log.debug("login data invalid account:{}", loginDto.getAccount());
            return ResponseEntity.badRequest().body(generateInvalidResponse());
        }

        return accountMyBatisService.login(loginDto)
                .map(AccessTokenDto::new)
                .map(this::generateSuccessResponse)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity
                    .badRequest().body(generateInvalidResponse()));

    }

    private boolean isLoginDataCorrect(LoginDto loginDto){
        return ValidateUtil.isAccountCorrect(loginDto.getAccount()) &&
                ValidateUtil.isPasswordCorrect(loginDto.getPassword());
    }

    private CommonResponse<AccessTokenDto> generateInvalidResponse(){
        StatusCode statusCode = StatusCode.InvalidData;

        return new CommonResponse<AccessTokenDto>().setStatus(statusCode.getValue())
                                     .setErrorMessage( "account or password is incorrect");
    }

    private CommonResponse<AccessTokenDto> generateSuccessResponse(AccessTokenDto token){
        return new CommonResponse<AccessTokenDto>().setBody(token);
    }
}
