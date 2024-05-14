package org.mjulikelion.memomanagement.dto.response.memo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
@AllArgsConstructor
public class MemoResponseData {
    // 메모를 조회하면 메모의 Id, title, content를 보여준다.
    private UUID id;
    private String title;
    private String content;
}

