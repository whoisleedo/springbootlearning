package com.practice.demo.controller;

import com.practice.demo.dto.CommonResponse;
import com.practice.demo.dto.ResetPasswordDto;
import com.practice.demo.dto.StatusCode;
import com.practice.demo.sevice.ResetPasswordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api")
public class ResetPasswordController {
    private static final Logger log = LoggerFactory.getLogger(ResetPasswordController.class);

    private final ResetPasswordService resetPasswordService;
    @Autowired
    public ResetPasswordController(ResetPasswordService resetPasswordService) {
        this.resetPasswordService = resetPasswordService;
    }


    @PostMapping("users/resetPassword")
    public ResponseEntity<CommonResponse> resetPassword(@RequestBody ResetPasswordDto resetPasswordDto,
                                           Authentication authentication){
        final String loginUserAccount =  (String)authentication.getPrincipal();
        log.debug("user Account:{} resetPassword" ,loginUserAccount);
        StatusCode statusCode;
        try{
            statusCode = resetPasswordService.resetPassword(loginUserAccount,resetPasswordDto)
                    .orElse(StatusCode.InvalidData);

        }catch (Exception e){
            log.warn("resetPassword error", e);
            statusCode = StatusCode.InternalError;

        }


        return generateResponse(statusCode);

    }
    private String convertStatusToMessage(StatusCode statusCode){
        switch (statusCode){
            case OK:
                return "reset success";
            case InvalidData:
                return "reset data error";
            case InternalError:
                return "internal_error";
            default:
                log.warn("unknown_status :{}" ,statusCode);
                return "unknown_status";
        }
    }

    private ResponseEntity<CommonResponse> generateResponse(StatusCode statusCode){
        CommonResponse response =
                new CommonResponse(statusCode.getValue(), convertStatusToMessage(statusCode));

        switch (statusCode){
            case OK:
                return ResponseEntity.accepted().body(response);
            case InvalidData:
                return ResponseEntity.badRequest().body(response);
            case InternalError:
                return ResponseEntity.internalServerError().body(response);
            default:
                log.warn("unknown_status :{}" ,statusCode);
                return ResponseEntity.internalServerError().body(response);

        }

    }


}
