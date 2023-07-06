package com.practice.demo.controller;


import com.practice.demo.dto.common.CommonResponse;
import com.practice.demo.dto.UserDto;
import com.practice.demo.dto.common.StatusCode;
import com.practice.demo.sevice.UserService;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api")
@Tag(name = "query user by id api", description = "API for query user by id")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @Operation(summary = "find user by id", description = "find user by id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "query user success",
                            content = @Content(mediaType = "application/json",
                                    examples = @ExampleObject(name = "success",
                                            value = "{\n" +
                                                    "    \"status\": 1,\n" +
                                                    "    \"errorMessage\": \"success\",\n" +
                                                    "    \"body\": {\n" +
                                                    "        \"id\": 1,\n" +
                                                    "        \"name\": \"test001\",\n" +
                                                    "        \"email\": \"test1@gmail.com\",\n" +
                                                    "        \"account\": \"test1\"\n" +
                                                    "    }\n" +
                                                    "}"))

                    ),
                    @ApiResponse(responseCode = "404", description = "user not found",
                            content = @Content(mediaType = "application/json",
                                    examples = @ExampleObject(name = "not found",
                                            value = "{\n" +
                                                    "    \"status\": -5,\n" +
                                                    "    \"errorMessage\": \"not found\",\n" +
                                                    "    \"body\": null" +
                                                    "}"))

                    )
            }
    )
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("users/{id}")
    public ResponseEntity<CommonResponse<UserDto>> getAccountUser(
            @Parameter(description = "userId", required = true, example = "1") @PathVariable long id ,
                                                                        Authentication authentication){
        String loginUserAccount =  (String)authentication.getPrincipal();
        log.debug("user Account:{} query userId:{}" ,loginUserAccount,id);
        return userService.getUserById(id)
                .map(this::generateResponse)
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    CommonResponse<UserDto> notFound = new CommonResponse<UserDto>()
                            .setStatus(StatusCode.NotFound.getValue())
                            .setErrorMessage("not found");
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(notFound);
                });

    }

    private CommonResponse<UserDto> generateResponse(UserDto userDto){
        return new CommonResponse<UserDto>().setBody(userDto);

    }



}
