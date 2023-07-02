package com.practice.demo.dto;

public class AccountDto {
    private String account;
    private String password;
    private String name;
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
