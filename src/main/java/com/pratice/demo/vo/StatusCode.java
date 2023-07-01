package com.pratice.demo.vo;

public enum StatusCode {
    OK(1),
    InvalidData(-1),
    InternalError(-2),
    Account_Unavailable(-3);

    private final int value;

    private StatusCode(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
