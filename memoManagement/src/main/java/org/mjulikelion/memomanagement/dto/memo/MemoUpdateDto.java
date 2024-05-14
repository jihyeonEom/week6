package org.mjulikelion.memomanagement.dto.memo;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class MemoUpdateDto {
    @NotBlank(message = "제목이 비어있습니다.")
    private String title;

    @NotBlank(message = "내용이 비어있습니다.")
    private String content;
}
