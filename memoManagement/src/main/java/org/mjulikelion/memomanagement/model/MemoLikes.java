package org.mjulikelion.memomanagement.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Entity(name = "memo_likes")
public class MemoLikes extends BaseEntity {
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Memo memo;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private User user;
}