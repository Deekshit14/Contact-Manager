package com.scm.forms;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserForm {

    @NotBlank (message = "Username is required")
    @Size (min = 3, message = "Min 3 characters is required")
    private String name;

    @Email (message = "Invalid E-mail")
    @NotBlank (message = "E-mail is required")
    private String email;

    @NotBlank (message = "Password required")
    @Size (min = 6, message = "Min 6 characters is required")
    private String password;

//    @NotBlank (message = "PhoneNumber required")
    @Size (min = 10, max = 10, message = "Invalid Phone Number")
    private String phoneNumber;

    @NotBlank (message = "About is required")
    private String about;
}
