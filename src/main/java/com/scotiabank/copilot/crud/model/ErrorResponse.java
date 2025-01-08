package com.scotiabank.copilot.crud.model;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
public class ErrorResponse {

    private int status;
    private String message;
    private String error;
    private LocalDateTime timestamp;

    // Constructor
    public ErrorResponse(int status, String message, String error) {
        this.status = status;
        this.message = message;
        this.error = error;
        this.timestamp = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "ErrorResponse{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", error='" + error + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
