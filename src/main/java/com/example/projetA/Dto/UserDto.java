package com.example.projetA.Dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class UserDto {
    private String name;

    private String password;

    private String email;

    //private enum Role{ADMIN, USER};

    //@Enumerated(EnumType.STRING)
    private String role;
}
