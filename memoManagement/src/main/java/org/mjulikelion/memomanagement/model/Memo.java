package org.mjulikelion.memomanagement.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.mjulikelion.memomanagement.dto.memo.MemoUpdateDto;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "memo")
public class Memo extends BaseEntity {
    @Column(length = 100, nullable = false)// 길이는 100자 이하이고, 비어있을 수 없다.
    private String title;

    @Column(length = 2000, nullable = false)// 길이는 2000자 이하이고, 비어있을 수 없다.
    private String content;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private User user;

    @OneToMany(mappedBy = "memo", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<MemoLikes> memoLikes;

    public void updateMemo(MemoUpdateDto memoUpdateDto) {
        this.title = memoUpdateDto.getTitle();
        this.content = memoUpdateDto.getContent();
    }

    public void addLike(MemoLikes memoLikes) {
        this.memoLikes.add(memoLikes);
    }
}
