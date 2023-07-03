package com.practice.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotNull;

public class CommonResponse {
    @NotNull
    @Schema(description = "status number")
    private Integer status;
    @NotNull
    @Schema(description = "message for result")
    private String errorMessage;

    public CommonResponse(Integer status, String errorMessage) {
        this.status = status;
        this.errorMessage = errorMessage;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer(super.toString());
        sb.append("{status:").append(status);
        sb.append(", errorMessage:'").append(errorMessage).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
