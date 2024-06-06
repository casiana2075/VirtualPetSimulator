package org.example;

import lombok.*;

@AllArgsConstructor @Getter
public final class Result<T> {
    private T data;
    private String error;
    private boolean success;

    public static <T> Result<T> success(T data) {
        return new Result<>(data, null, true);
    }

    public static <T> Result<T> failure(String error) {
        return new Result<>(null, error, false);
    }
}