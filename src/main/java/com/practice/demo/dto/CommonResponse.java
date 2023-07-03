package com.practice.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotNull;

public class CommonResponse<T> {
    @NotNull
    @Schema(description = "status number")
    private Integer status;
    @NotNull
    @Schema(description = "message of result")
    private String errorMessage;
    @Schema(description = "body of response")
    private T body;

    public CommonResponse(Integer status, String errorMessage) {
        this.status = status;
        this.errorMessage = errorMessage;
    }

    public CommonResponse(Integer status, String errorMessage, T body) {
        this.status = status;
        this.errorMessage = errorMessage;
        this.body = body;
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

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer(super.toString());
        sb.append("{status:").append(status);
        sb.append(", errorMessage:'").append(errorMessage).append('\'');
        sb.append(", body:").append(body);
        sb.append('}');
        return sb.toString();
    }
}
