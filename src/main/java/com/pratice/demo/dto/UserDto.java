package com.pratice.demo.dto;

public class UserDto {

    private long id;
    private String name;
    private String email;
    private String account;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer(super.toString());
        sb.append("{id:").append(id);
        sb.append(", name:'").append(name).append('\'');
        sb.append(", email:'").append(email).append('\'');
        sb.append(", account:'").append(account).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
