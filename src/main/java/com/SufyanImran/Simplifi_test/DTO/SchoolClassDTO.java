package com.SufyanImran.Simplifi_test.dto;

import jakarta.validation.constraints.NotBlank;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SchoolClassDTO {

    @NotBlank(message = "Class name is required")
    @Schema(example = "Grade 10")
    private String name;

}
