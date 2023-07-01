package com.pratice.demo.entity;

import org.springframework.lang.NonNull;

import javax.persistence.*;

@Entity
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    @Column(name = "user_account")
    private String userAccount;
    @NonNull
    @Column(name = "password")
    private String password;
    @NonNull
    @Column(name = "name")
    private String name;
    @Column(name = "email")
    private String email;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
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

    public Account() {
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer(super.toString());
        sb.append("{id:").append(id);
        sb.append(", userAccount:'").append(userAccount).append('\'');
        sb.append(", name:'").append(name).append('\'');
        sb.append(", email:'").append(email).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
