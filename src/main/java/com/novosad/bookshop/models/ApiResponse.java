package com.novosad.bookshop.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ApiResponse {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;
    private Object body;

    public ApiResponse() {
        timestamp = LocalDateTime.now();
    }

    public ApiResponse(Object body) {
        this();
        this.body = body;
    }
}
