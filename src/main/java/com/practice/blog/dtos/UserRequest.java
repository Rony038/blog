package com.practice.blog.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    private long id;

    @NotBlank(message = "Username is required.")
    @Size(message = "Username must not exceed 20 characters.")
    @Schema(description = "User name of a user")
    private String userName;

    @Email(message = "Invalid email format.")
    @NotBlank(message = "Email is required.")
    @Schema(description = "Email of a user")
    private String email;

    @NotBlank(message = "Password is required.")
    @Size(min = 8, message = "Password should contain minimum 8 characters.")
    @Schema(description = "Password to login")
    private String password;
}
