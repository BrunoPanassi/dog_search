package com.dogsearch.demo.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String name;
    private String city;
    private String neighbourhood;
    private String phoneNumber;
    private String password;
    private String email;
}
