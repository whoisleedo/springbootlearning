package com.practice.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotNull;

public class ResetPasswordDto {
    @NotNull
    @Schema(description = "old password",minLength = 5 , maxLength = 30)
    private String currentPassword;
    @NotNull
    @Schema(description = "new password",minLength = 5 , maxLength = 30)
    private String newPassword;

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public ResetPasswordDto() {
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(super.toString());
        sb.append("{currentPassword:'").append(currentPassword).append('\'');
        sb.append(", newPassword:'").append(newPassword).append('\'');
        sb.append("}");
        return sb.toString();
    }
}
