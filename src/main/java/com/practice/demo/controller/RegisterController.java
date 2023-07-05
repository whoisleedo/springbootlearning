package com.practice.demo.controller;


import com.practice.demo.dto.AccountDto;
import com.practice.demo.dto.common.CommonResponse;
import com.practice.demo.dto.common.StatusCode;
import com.practice.demo.exception.AccountUnavailableException;
import com.practice.demo.sevice.RegisterService;
import com.practice.demo.util.ValidateUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Optional;


@RestController
@RequestMapping("api")
@Tag(name = "register user api", description = "API for register user")
public class RegisterController {

    private static final Logger log = LoggerFactory.getLogger(RegisterController.class);

    private final RegisterService registerService;
    @Autowired
    public RegisterController(RegisterService registerService) {
        this.registerService = registerService;
    }

    @Operation(summary = "register api", description = "register api")
    @PostMapping("users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "register success"),
            @ApiResponse(responseCode = "400", description = "invalid data"),
            @ApiResponse(responseCode = "409", description = "account unavailable"),
            @ApiResponse(responseCode = "500", description = "internal error")
    })
    public ResponseEntity<CommonResponse<?>> register(@RequestBody AccountDto accountVo) {
        StatusCode statusCode;
        Long accountId = null;
        if (!isRegisterDataValid(accountVo)) {
            statusCode = StatusCode.InvalidData;
            log.debug("invalid account data when register :{}", accountVo);
            return generateResponse(statusCode, accountId);
        }

        try {
            accountId = registerService.registerAccount(accountVo);
            statusCode = StatusCode.OK;
            // do something in db
        } catch (AccountUnavailableException accountUnavailableException) {
            log.debug("account unavailable account:{}", accountVo.getAccount());
            statusCode = StatusCode.Account_Unavailable;

        } catch (Exception exception) {
            log.warn("other error when register accountVo:{}", accountVo, exception);
            statusCode = StatusCode.InvalidData;

        }
        log.debug("end of register statusCode:{} , accountId:{}" , statusCode,accountId);
        return generateResponse(statusCode, accountId);
    }
    
    private ResponseEntity<CommonResponse<?>> generateResponse(StatusCode statusCode, Long accountId){
        CommonResponse<?> response =
                new CommonResponse<>().setStatus(statusCode.getValue())
                        .setErrorMessage(convertStatusToMessage(statusCode));

        switch (statusCode){
            case OK:
                return ResponseEntity.created(URI.create("/api/users/" + accountId))
                        .body(response);
            case InvalidData:
                return ResponseEntity.badRequest().body(response);
            case InternalError:
                return ResponseEntity.internalServerError().body(response);
            case Account_Unavailable:
                return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
            default:
                log.warn("unknown_status :{}" ,statusCode);
                return ResponseEntity.internalServerError().body(response);

        }

    }

    private boolean isRegisterDataValid(AccountDto accountVo){
        return Optional.ofNullable(accountVo)
                .filter(vo -> ValidateUtil.isAccountCorrect(accountVo.getAccount()))
                .filter(vo -> ValidateUtil.isPasswordCorrect(accountVo.getPassword()))
                .filter(vo -> StringUtils.hasText(vo.getName()) && vo.getName().length() < 30)
                .filter(vo -> isEmailValid(vo.getEmail()))
                .isPresent();
    }

    private boolean isEmailValid(String email){
        return email == null || ValidateUtil.isValidEmail(email);
    }

    private String convertStatusToMessage(StatusCode statusCode){
        switch (statusCode){
            case OK:
                return "success";
            case InvalidData:
                return "invalid_input";
            case InternalError:
                return "internal_error";
            case Account_Unavailable:
                return "account_unavailable";
            default:
                log.warn("unknown_status :{}" ,statusCode);
                return "unknown_status";
        }
    }




}
