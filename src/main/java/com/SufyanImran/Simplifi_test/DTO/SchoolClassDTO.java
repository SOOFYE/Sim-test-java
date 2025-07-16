package com.SufyanImran.Simplifi_test.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SchoolClassDTO {

    @NotBlank(message = "Class name is required")
    private String name;

}
