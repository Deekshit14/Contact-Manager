package com.scm.forms;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactForm {

    @NotBlank (message = "Name is required")
    private String name;

    @NotBlank (message = "Email is required")
    @Email (message = "Invalid Email")
    private String email;

    @NotBlank (message = "Phone number is required")
    @Size (min = 10, max = 10, message = "Invalid Phone Number")
//    @Pattern (regexp = "^[0-9]{10}$", message = "Invalid Phone number")
    private String phoneNumber;

    @NotBlank (message = "Address is required")
    private String address;
    private String description;
    private String websiteLink;
    private String linkedInLink;
    private boolean favorite;

    // we will create an annotation which validates our file i.e, size, resolution, type

    private MultipartFile contactImage;

}