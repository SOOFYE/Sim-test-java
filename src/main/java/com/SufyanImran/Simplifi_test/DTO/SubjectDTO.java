package com.SufyanImran.Simplifi_test.dto;

import jakarta.validation.constraints.NotBlank;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubjectDTO {

    @NotBlank(message = "Name is mandatory")
    @Schema(example = "Mathematics")
    private String name;
}
