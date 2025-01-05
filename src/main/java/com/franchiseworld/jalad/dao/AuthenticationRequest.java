package com.franchiseworld.jalad.dao;

import lombok.Data;

@Data
public class AuthenticationRequest {
    private String email;
    private String password;


}