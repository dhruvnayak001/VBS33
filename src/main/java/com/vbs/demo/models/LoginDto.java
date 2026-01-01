package com.vbs.demo.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor

public class LoginDto {

    @Column(nullable = false,unique = true)
    String username;
    String password;
    String role;
}
