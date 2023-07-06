package com.practice.demo.dto.common;

public enum StatusCode {
    OK(1),
    InvalidData(-1),
    InternalError(-2),
    AccountUnavailable(-3),
    InvalidToken(-4),
    NotFound(-5);

    private final int value;

    StatusCode(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
