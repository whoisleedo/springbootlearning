package com.practice.demo.dto;

public class ResetPasswordDto {
    private String currentPassword;
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
