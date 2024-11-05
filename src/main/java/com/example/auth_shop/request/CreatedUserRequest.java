package com.example.auth_shop.request;

import lombok.Data;

@Data
public class CreatedUserRequest {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
