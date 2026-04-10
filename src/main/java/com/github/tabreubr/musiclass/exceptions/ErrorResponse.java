package com.github.tabreubr.musiclass.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ErrorResponse {

    private Integer status;
    private String message;
    private LocalDateTime timestamp;
}
