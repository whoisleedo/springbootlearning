package com.pratice.demo.controller;


import com.pratice.demo.exception.AccountUnavailableException;
import com.pratice.demo.sevice.RegisterService;
import com.pratice.demo.util.ValidateUtil;
import com.pratice.demo.dto.AccountDto;
import com.pratice.demo.dto.CommonResponse;
import com.pratice.demo.dto.StatusCode;

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
public class RegisterController {

    private static final Logger log = LoggerFactory.getLogger(RegisterController.class);

    private final RegisterService registerService;
    @Autowired
    public RegisterController(RegisterService registerService) {
        this.registerService = registerService;
    }


    @PostMapping("users/register")
    public ResponseEntity<CommonResponse> register(@RequestBody AccountDto accountVo) {
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




    private ResponseEntity<CommonResponse> generateResponse(StatusCode statusCode, Long accountId){
        CommonResponse response =
                new CommonResponse(statusCode.getValue(), convertStatusToMessage(statusCode));

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
                .filter(vo -> StringUtils.hasText(vo.getAccount()) && vo.getAccount().length() < 15)
                .filter(vo -> StringUtils.hasText(vo.getPassword()) && vo.getPassword().length() > 5)
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
