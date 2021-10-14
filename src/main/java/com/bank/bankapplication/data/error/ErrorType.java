package com.bank.bankapplication.data.error;

public enum ErrorType {
    NOT_FOUND,
    INVALID_REQUEST_DATA,
    UNEXPECTED_ERROR,
    DATA_ALREADY_EXISTS;

    public static ErrorType fromValue(String v) {
        return valueOf(v);
    }
}
