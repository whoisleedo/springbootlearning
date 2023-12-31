package com.practice.demo.controller;


import com.practice.demo.dto.common.CommonResponse;
import com.practice.demo.dto.ResetPasswordDto;
import com.practice.demo.dto.common.StatusCode;
import com.practice.demo.sevice.ResetPasswordService;
import com.practice.demo.util.ValidateUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("api")
@Tag(name = "reset password api", description = "API for reset user password")
public class ResetPasswordController {
    private static final Logger log = LoggerFactory.getLogger(ResetPasswordController.class);

    private final ResetPasswordService resetPasswordService;
    @Autowired
    public ResetPasswordController(ResetPasswordService resetPasswordService) {
        this.resetPasswordService = resetPasswordService;
    }

    @Operation(summary = "reset password", description = "reset password",
            requestBody =
            @io.swagger.v3.oas.annotations.parameters.RequestBody(content =
            @Content(mediaType = "application/json",
                    examples = @ExampleObject(name = "request",
                            value = "{\n" +
                                    "    \"currentPassword\":\"oldPassword\",\n" +
                                    "    \"newPassword\":\"newPassword\"\n" +
                                    "}"))),
            responses = {
                    @ApiResponse(responseCode = "202", description = "reset success",
                            content = @Content(mediaType = "application/json",
                                    examples = @ExampleObject(name = "success",
                                            value = "{\n" +
                                                    "\"status\": 1,\n" +
                                                    "\"errorMessage\": \"reset success\",\n" +
                                                    "\"body\": null\n" +
                                                    "}"))

                    ),
                    @ApiResponse(responseCode = "400", description = "invalid_input",
                            content = @Content(mediaType = "application/json",
                                    examples = @ExampleObject(name = "input error",
                                            value = "{\n" +
                                                    "\"status\": -1,\n" +
                                                    "\"errorMessage\": \"reset data error\",\n" +
                                                    "\"body\": null\n" +
                                                    "}"))

                    ),
                    @ApiResponse(responseCode = "500", description = "internal error",
                            content = @Content(mediaType = "application/json",
                                    examples = @ExampleObject(name = "other exception",
                                            value = "{\n" +
                                                    "\"status\": -2,\n" +
                                                    "\"errorMessage\": \"internal_error\",\n" +
                                                    "\"body\": null\n" +
                                                    "}"))

                    )
            }
    )
    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping("users/{id}/password")
    public ResponseEntity<CommonResponse<?>> resetPassword(
            @RequestBody ResetPasswordDto resetPasswordDto,
            @Parameter(description = "User ID", example = "1")@PathVariable long id,
                                           Authentication authentication){
        final String loginUserAccount =  (String)authentication.getPrincipal();
        log.debug("user Account:{} resetPassword" ,loginUserAccount);
        StatusCode statusCode;

        if(!isRegisterDataValid(resetPasswordDto)){
           log.debug("user Account:{} resetPassword not valid" ,loginUserAccount);
            statusCode = StatusCode.InvalidData;
            return generateResponse(statusCode);
        }
        try{
            statusCode = resetPasswordService.resetPassword(id,loginUserAccount,resetPasswordDto)
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
    private boolean isRegisterDataValid(ResetPasswordDto resetPasswordDto){
        return Optional.ofNullable(resetPasswordDto)
                .filter(vo -> ValidateUtil.isPasswordCorrect(resetPasswordDto.getCurrentPassword()))
                .filter(vo -> ValidateUtil.isPasswordCorrect(resetPasswordDto.getNewPassword()))
                .isPresent();
    }

    private ResponseEntity<CommonResponse<?>> generateResponse(StatusCode statusCode){
        CommonResponse<?> response =
                new CommonResponse<>().setStatus(statusCode.getValue())
                                      .setErrorMessage(convertStatusToMessage(statusCode));
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
