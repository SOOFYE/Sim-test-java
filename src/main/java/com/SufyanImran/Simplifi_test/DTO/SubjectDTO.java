package com.SufyanImran.Simplifi_test.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubjectDTO {

    @NotBlank(message = "Name is mandatory")
    private String name;
}
