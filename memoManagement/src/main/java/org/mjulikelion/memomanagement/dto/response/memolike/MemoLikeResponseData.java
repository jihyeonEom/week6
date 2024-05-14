package org.mjulikelion.memomanagement.dto.response.memolike;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
public class MemoLikeResponseData {
    private List<String> userName; // 좋아요를 누른 유저의 이름
    private int count; // 좋아요의 갯수
}
