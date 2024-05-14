package org.mjulikelion.memomanagement.service;

import lombok.AllArgsConstructor;
import org.mjulikelion.memomanagement.dto.memo.MemoCreateDto;
import org.mjulikelion.memomanagement.dto.memo.MemoUpdateDto;
import org.mjulikelion.memomanagement.dto.response.memo.MemoListResponseData;
import org.mjulikelion.memomanagement.dto.response.memo.MemoResponseData;
import org.mjulikelion.memomanagement.dto.response.memolike.MemoLikeResponseData;
import org.mjulikelion.memomanagement.errorcode.ErrorCode;
import org.mjulikelion.memomanagement.exception.ForbiddenException;
import org.mjulikelion.memomanagement.exception.NotFoundException;
import org.mjulikelion.memomanagement.model.Memo;
import org.mjulikelion.memomanagement.model.MemoLikes;
import org.mjulikelion.memomanagement.model.User;
import org.mjulikelion.memomanagement.repository.MemoLikesRepository;
import org.mjulikelion.memomanagement.repository.MemoRepository;
import org.mjulikelion.memomanagement.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class MemoService {

    private final MemoRepository memoRepository;
    private final UserRepository userRepository;
    private final MemoLikesRepository memoLikesRepository;

    // 메모 작성하기
    public void createMemo(MemoCreateDto memoCreateDto, UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));
        List<MemoLikes> memoLikes = new ArrayList<>(); // 빈 좋아요 리스트 생성
        Memo memo = Memo.builder()
                .title(memoCreateDto.getTitle())
                .content(memoCreateDto.getContent())
                .user(user)
                .memoLikes(memoLikes)
                .build();

        this.memoRepository.save(memo);
    }

    // 유저가 작성한 모든 메모 조회하기
    public MemoListResponseData getAllMemoByUserId(UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));
        List<Memo> memos = user.getMemos();
        List<MemoResponseData> memoListResponseData = new ArrayList<>();

        for (Memo memo : memos) {
            MemoResponseData memoResponseData = MemoResponseData.builder()
                    .id(memo.getId())
                    .title(memo.getTitle())
                    .content(memo.getContent())
                    .build();
            memoListResponseData.add(memoResponseData);
        }
        return new MemoListResponseData(memoListResponseData);
    }


    // 메모를 메모 아이디를 통해 조회
    public MemoResponseData getMemoByMemoId(UUID memoId) {
        Memo memo = this.memoRepository.findById(memoId).orElseThrow(() -> new NotFoundException(ErrorCode.MEMO_NOT_FOUND));
        return new MemoResponseData(memo.getId(), memo.getTitle(), memo.getContent());
    }

    // 메모에 좋아요 누르기
    public void addLikeByMemoId(UUID userId, UUID memoId) {
        Memo memo = this.memoRepository.findById(memoId).orElseThrow(() -> new NotFoundException(ErrorCode.MEMO_NOT_FOUND));
        User user = this.userRepository.findById(userId).orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));

        MemoLikes memoLikes = new MemoLikes(memo, user);

        memo.addLike(memoLikes);
        this.memoRepository.save(memo);
    }

    // 해당 메모의 좋아요를 누른 유저의 이름과 좋아요 갯수 반환
    public MemoLikeResponseData getLikeByMemoId(UUID memoId) {
        Memo memo = this.memoRepository.findById(memoId).orElseThrow(() -> new NotFoundException(ErrorCode.MEMO_NOT_FOUND));
        List<MemoLikes> memoLike = memo.getMemoLikes();
        List<String> likeUserList = this.getLikeUserList(memoLike); // 메모에 좋아요르 누른 유저 list에서 유저의 이름 list를 반환
        int count = likeUserList.size();
        return new MemoLikeResponseData(likeUserList, count);
    }

    // 해당 유저가 작성한 메모를 메모 아이디를 통해 삭제
    public void deleteMemoByMemoId(UUID memoId) {
        Memo memo = this.memoRepository.findById(memoId).orElseThrow(() -> new NotFoundException(ErrorCode.MEMO_NOT_FOUND));
        List<MemoLikes> memoLikes = memo.getMemoLikes();

        for (MemoLikes memoLike : memoLikes) {
            this.memoLikesRepository.deleteById(memoLike.getId());
        }

        this.memoRepository.deleteById(memoId);
    }

    // 해당 유저가 작성한 메모를 메모 아이디를 통해 수정
    public void updateMemoByMemoId(MemoUpdateDto memoUpdateDto, UUID memoId) {
        Memo memo = this.memoRepository.findById(memoId).orElseThrow(() -> new NotFoundException(ErrorCode.MEMO_NOT_FOUND));
        memo.updateMemo(memoUpdateDto);
        this.memoRepository.save(memo);
    }

    // 좋아요 리스트에서 유저의 이름 반환
    public List<String> getLikeUserList(List<MemoLikes> memoLikesList) {
        List<String> nameList = new ArrayList<>();
        for (MemoLikes memoLikes : memoLikesList) {
            nameList.add(memoLikes.getUser().getName());
        }
        return nameList;
    }

    public void validateMemoById(UUID memoId) {
        if (!memoRepository.existsById(memoId)) {
            throw new NotFoundException(ErrorCode.MEMO_NOT_FOUND);
        }
    }

    // 유저가 해당 메모에 접근할 권한이 있는지 검사
    public void isUserHaveAccessTo(UUID userId, UUID memoId) {
        User user = this.userRepository.findById(userId).orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));
        for (Memo memo : user.getMemos()) {
            if (memo.getId().equals(memoId)) {
                return;
            }
        }
        throw new ForbiddenException(ErrorCode.USER_DOES_NOT_HAVE_ACCESS);
    }
}
