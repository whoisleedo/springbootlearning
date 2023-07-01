package com.pratice.demo.dto;

public class CommonResponse {
    private Integer status;
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
