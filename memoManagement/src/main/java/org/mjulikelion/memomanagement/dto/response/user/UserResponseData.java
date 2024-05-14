package org.mjulikelion.memomanagement.dto.response.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.mjulikelion.memomanagement.dto.response.memo.MemoResponseData;
import org.mjulikelion.memomanagement.dto.response.userorganization.UserOrganizationResponseData;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
public class UserResponseData {
    private String name;
    private List<MemoResponseData> memos;
    private List<UserOrganizationResponseData> userOrganizations;
}
