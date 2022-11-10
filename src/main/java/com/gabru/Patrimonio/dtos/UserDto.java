package com.gabru.Patrimonio.dtos;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
@Data
public class UserDto {
    @NotBlank @NotNull
    String nombre;
    @NotBlank @NotNull
    String password;
    @NotBlank @NotNull @Email
    String email;
}
