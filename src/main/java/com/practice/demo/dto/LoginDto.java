package com.practice.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotNull;

public class LoginDto {
    @NotNull
    @Schema(description = "login user account")
    private String account;
    @NotNull
    @Schema(description = "login user password")
    private String password;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LoginDto() {
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(super.toString());
        sb.append("{account:'").append(account).append('\'');
        sb.append("}");
        return sb.toString();
    }
}
