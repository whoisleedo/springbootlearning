package com.practice.demo.dto.common;

public enum StatusCode {
    OK(1),
    InvalidData(-1),
    InternalError(-2),
    Account_Unavailable(-3),

    Invalid_Token(-4);

    private final int value;

    StatusCode(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
