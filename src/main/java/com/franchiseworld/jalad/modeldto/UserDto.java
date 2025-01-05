package com.franchiseworld.jalad.modeldto;
import com.franchiseworld.jalad.model.Role;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UserDto {
    private String email;
    private String password;
    private Role role;
}

