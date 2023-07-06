package ru.clevertec.cache.exceptions;

import lombok.Getter;

@Getter
public enum ErrorCode {

    NOT_FOUND(404), INCORRECT_ID(400);

    private final int code;

    ErrorCode(int code) {
        this.code = code;
    }
}
