package com.github.tabreubr.musiclass.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorObject {

    private String field;
    private String message;
}
