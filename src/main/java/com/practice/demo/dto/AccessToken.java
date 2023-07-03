package com.practice.demo.dto;


import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotNull;

public class AccessToken {
    @NotNull
    @Schema(description = "jwt token")
    private String accessToken;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public AccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public AccessToken() {
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer(super.toString());
        sb.append("{accessToken:'").append(accessToken).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
