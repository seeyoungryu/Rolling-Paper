package com.sparta.rollingpaper.jwt;

public enum ErrorCode {
    UNEXPECTED_ERROR("An unexpected error occurred.");

    private final String message;

    ErrorCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
