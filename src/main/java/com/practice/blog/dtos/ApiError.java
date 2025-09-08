package com.practice.blog.dtos;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
public class ApiError {
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private Map<String, String> details;
}