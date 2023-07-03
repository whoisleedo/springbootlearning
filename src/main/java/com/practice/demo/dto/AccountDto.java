package com.practice.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotNull;

public class AccountDto {
    @NotNull
    @Schema(description = "register user's account",minLength = 5 , maxLength = 15)
    private String account;
    @NotNull
    @Schema(description = "register user's password",minLength = 5, maxLength = 30)
    private String password;
    @NotNull
    @Schema(description = "register user's name" , minLength = 1 , maxLength = 30)
    private String name;
    @Schema(description = "register user's email" , maxLength = 255)
    private String email;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer(super.toString());
        sb.append("{account:'").append(account).append('\'');
        sb.append(", name:'").append(name).append('\'');
        sb.append(", email:'").append(email).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public AccountDto() {
    }
}
