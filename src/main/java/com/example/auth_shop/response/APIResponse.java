package com.example.auth_shop.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class APIResponse {
    private LocalDateTime dateTime;
    private String message;
    private Object data;

    public APIResponse( String message, Object data) {
        this.message = message;
        this.data = data;
    }
}
