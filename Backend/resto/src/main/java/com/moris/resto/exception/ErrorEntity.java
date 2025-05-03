package com.moris.resto.exception;

public record ErrorEntity(
        String code,
        String message
) {
}
