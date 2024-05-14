package org.mjulikelion.memomanagement.dto.organization;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class OrganizationCreateDto {
    @NotBlank(message = "이름이 비어있습니다.")
    private String name;
}
