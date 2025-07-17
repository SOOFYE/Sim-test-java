package com.SufyanImran.Simplifi_test.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeacherDTO {

    @NotBlank(message = "First name is required")
    @Schema(example = "Alice")
    private String firstname;

    @NotBlank(message = "Last name is required")
    @Schema(example = "Johnson")
    private String lastname;

    @Email(message = "Email must be valid")
    @Schema(example = "alice.johnson@example.com")
    private String emailAddress;

    @Pattern(regexp = "\\d{10}", message = "Phone number must be 10 digits")
    @Schema(example = "9876543210")
    private String phoneNumber;
}