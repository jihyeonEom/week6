package org.mjulikelion.memomanagement.model;

import jakarta.persistence.*;
import lombok.*;
import org.mjulikelion.memomanagement.dto.user.UserUpdateDto;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "user")
public class User extends BaseEntity {
    @Setter
    @Column(length = 100, nullable = false)// 길이는 100자 이하이고, 비어있을 수 없다.
    private String email;

    @Setter
    @Column(length = 100, nullable = false)
    private String password;

    @Setter
    @Column(length = 100, nullable = false)// 길이는 100자 이하이고, 비어있을 수 없다.
    private String name;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Memo> memos;

    @Setter
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<MemoLikes> memoLikes;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<UserOrganization> userOrganizations;

    public void updateUser(UserUpdateDto userUpdateDto) {
        setEmail(userUpdateDto.getEmail());
        setPassword(userUpdateDto.getPassword());
        setName(userUpdateDto.getName());
    }
}
