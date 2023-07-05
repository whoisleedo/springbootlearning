package com.practice.demo.dto.common;

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


    public CommonResponse() {
        this(StatusCode.OK.getValue(), "success");
    }

    public Integer getStatus() {
        return status;
    }

    public CommonResponse<T> setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public CommonResponse<T> setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
        return this;
    }

    public T getBody() {
        return body;
    }

    public CommonResponse<T> setBody(T body) {
        this.body = body;
        return this;
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
