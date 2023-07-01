package com.pratice.demo.controller;


import com.pratice.demo.exception.AccountUnavailableException;
import com.pratice.demo.sevice.RegisterService;
import com.pratice.demo.util.ValidateUtil;
import com.pratice.demo.vo.AccountVo;
import com.pratice.demo.vo.CommonResponse;
import com.pratice.demo.vo.StatusCode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
    public CommonResponse register(@RequestBody AccountVo accountVo){
        StatusCode statusCode;
        if(!isRegisterDataValid(accountVo)){
            statusCode = StatusCode.InvalidData;
            log.debug("invalid account data when register :{}", accountVo);
            return generateResponse(statusCode);
        }
        try {
            registerService.registerAccount(accountVo);
            statusCode = StatusCode.OK;
            // do something in db
        }catch (AccountUnavailableException accountUnavailableException){
            log.debug("account unavailable account:{}" , accountVo.getAccount());
            statusCode = StatusCode.Account_Unavailable;


        }catch (Exception exception){
            log.warn("other error when register accountVo:{}" , accountVo, exception);
            statusCode = StatusCode.InvalidData;

        }



        return generateResponse(statusCode);
    }

    private CommonResponse generateResponse(StatusCode statusCode){
        return new CommonResponse(statusCode.getValue(),convertStatusToMessage(statusCode.getValue()));
    }

    private boolean isRegisterDataValid(AccountVo accountVo){
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

    private String convertStatusToMessage(int statusValue){
        switch (statusValue){
            case 0:
                return "success";
            case -1:
                return "invalid_input";
            case -2:
                return "internal_error";
            case -3:
                return "account_unavailable";
            default:
                log.warn("unknown_status_value :{}" , statusValue);
                return "unknown_status";
        }
    }




}
