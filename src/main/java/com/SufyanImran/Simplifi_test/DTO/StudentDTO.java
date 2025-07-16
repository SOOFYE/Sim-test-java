package com.SufyanImran.Simplifi_test.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentDTO {

    @Schema(example = "John", description = "First name of the student")
    @NotBlank(message = "First name is required")
    private String firstname;

    @Schema(example = "Doe", description = "Last name of the student")
    @NotBlank(message = "Last name is required")
    private String lastname;

    @Schema(example = "johndoe@example.com", description = "Email address of the student")
    @Email(message = "Invalid email address")
    private String emailAddress;

    @Schema(example = "1234567890", description = "10-digit phone number")
    @Pattern(regexp = "\\d{10}", message = "Phone number must be 10 digits")
    private String phoneNumber;
}
