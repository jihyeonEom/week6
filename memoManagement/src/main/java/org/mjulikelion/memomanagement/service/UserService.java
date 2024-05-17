package org.mjulikelion.memomanagement.service;

import lombok.AllArgsConstructor;
import org.mjulikelion.memomanagement.dto.response.memo.MemoResponseData;
import org.mjulikelion.memomanagement.dto.response.user.UserResponseData;
import org.mjulikelion.memomanagement.dto.response.userorganization.UserOrganizationResponseData;
import org.mjulikelion.memomanagement.dto.user.UserCreateDto;
import org.mjulikelion.memomanagement.dto.user.UserUpdateDto;
import org.mjulikelion.memomanagement.errorcode.ErrorCode;
import org.mjulikelion.memomanagement.exception.ConflictException;
import org.mjulikelion.memomanagement.exception.NotFoundException;
import org.mjulikelion.memomanagement.model.Memo;
import org.mjulikelion.memomanagement.model.MemoLikes;
import org.mjulikelion.memomanagement.model.User;
import org.mjulikelion.memomanagement.model.UserOrganization;
import org.mjulikelion.memomanagement.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // 유저의 회원가입
    public void createUser(UserCreateDto userCreateDto) {
        isEmailExist(userCreateDto.getEmail()); // 이메일 중복 검사

        List<Memo> memos = new ArrayList<>();
        List<MemoLikes> memoLikes = new ArrayList<>();
        List<UserOrganization> userOrganizations = new ArrayList<>();
        User user = User.builder()
                .email(userCreateDto.getEmail())
                .password(userCreateDto.getPassword())
                .name(userCreateDto.getName())
                .memos(memos)
                .memoLikes(memoLikes)
                .userOrganizations(userOrganizations)
                .build();
        this.userRepository.save(user);
    }

    // 이메일 중복 검사
    public void isEmailExist(String email) {
        User user = this.userRepository.findUserByEmail(email);
        if (user != null) {
            throw new ConflictException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }
    }

    // 회원 정보 조회
    public UserResponseData getUserInfo(User user) {
        List<MemoResponseData> memoResponseDataList = new ArrayList<>(); // 유저가 작성한 메모 리스트
        List<UserOrganizationResponseData> userOrganizationResponseDataList = new ArrayList<>(); // 유저가 가입한 org 이름 리스트

        for (Memo memo : user.getMemos()) {
            MemoResponseData memoResponseData = MemoResponseData.builder()
                    .id(memo.getId())
                    .title(memo.getTitle())
                    .content(memo.getContent())
                    .build();
            memoResponseDataList.add(memoResponseData);
        }

        for (UserOrganization userOrganization : user.getUserOrganizations()) {
            UserOrganizationResponseData userOrganizationResponseData = UserOrganizationResponseData.builder()
                    .name(userOrganization.getOrganization().getName())
                    .build();
            userOrganizationResponseDataList.add(userOrganizationResponseData);
        }

        return new UserResponseData(user.getName(), memoResponseDataList, userOrganizationResponseDataList);
    }

    // 유저 탈퇴
    public void deleteUser(User user) {
        this.userRepository.delete(user);
    }

    // 유저 정보 업데이트
    public void updateUser(UserUpdateDto userUpdateDto, User user) {
        user.updateUser(userUpdateDto);
        this.userRepository.save(user);
    }

    public void validateUserById(UUID userId) {
        User user = this.userRepository.findById(userId).orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));
        if (user == null) {
            throw new NotFoundException(ErrorCode.USER_NOT_FOUND);
        }
    }

}
