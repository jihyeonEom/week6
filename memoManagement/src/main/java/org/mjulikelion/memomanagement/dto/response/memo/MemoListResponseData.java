package org.mjulikelion.memomanagement.dto.response.memo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
public class MemoListResponseData {
    private List<MemoResponseData> memos;
}
