package io.github.nbgraciano.commerce_api.exception;


public record ErrorResponse(
        int status,
        String message
) {
}