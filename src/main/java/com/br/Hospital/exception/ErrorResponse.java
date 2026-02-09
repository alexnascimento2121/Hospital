package com.br.Hospital.exception;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class ErrorResponse {
    private int status;
    private String message;
    private LocalDateTime timestamp = LocalDateTime.now();

    public ErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
