package ru.clevertec.cache.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class LibraryException extends IllegalArgumentException {
    private final HttpStatus status;

    public LibraryException(String s, HttpStatus status) {
        super(s);
        this.status = status;
    }
}
