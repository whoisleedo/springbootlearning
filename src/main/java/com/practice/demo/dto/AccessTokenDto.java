package com.practice.demo.dto;


import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotNull;

public class AccessTokenDto {
    @NotNull
    @Schema(description = "jwt token")
    private String accessToken;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public AccessTokenDto(String accessToken) {
        this.accessToken = accessToken;
    }

    public AccessTokenDto() {
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer(super.toString());
        sb.append("{accessToken:'").append(accessToken).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
