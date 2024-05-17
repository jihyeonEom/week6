package org.mjulikelion.memomanagement.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.mjulikelion.memomanagement.authentication.user.AuthenticatedUser;
import org.mjulikelion.memomanagement.dto.ResponseDto;
import org.mjulikelion.memomanagement.dto.memo.MemoCreateDto;
import org.mjulikelion.memomanagement.dto.memo.MemoUpdateDto;
import org.mjulikelion.memomanagement.dto.response.memo.MemoListResponseData;
import org.mjulikelion.memomanagement.dto.response.memo.MemoResponseData;
import org.mjulikelion.memomanagement.dto.response.memolike.MemoLikeResponseData;
import org.mjulikelion.memomanagement.model.User;
import org.mjulikelion.memomanagement.service.MemoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@AllArgsConstructor
public class MemoController {

    private final MemoService memoService;

    // 메모 작성하기
    @PostMapping("/memos")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ResponseDto<Void>> createMemo(@RequestBody @Valid MemoCreateDto memoCreateDto,
                                                        @AuthenticatedUser User user) {
        this.memoService.createMemo(memoCreateDto, user);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.CREATED, "create memo"), HttpStatus.CREATED);
    }

    // 유저가 작성한 모든 메모 조회하기
    @GetMapping("/memos")
    public ResponseEntity<ResponseDto<MemoListResponseData>> getAllMemo(@AuthenticatedUser User user) {
        MemoListResponseData memoListResponseData = memoService.getAllMemo(user);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "get memos", memoListResponseData), HttpStatus.OK);
    }

    // 유저가 작성한 메모를 메모 아이디를 통해 조회
    @GetMapping("/memos/{memoId}")
    public ResponseEntity<ResponseDto<MemoResponseData>> getMemoByMemoId(@PathVariable("memoId") UUID memoId) {
        this.memoService.validateMemoById(memoId);
        MemoResponseData memoResponseData = memoService.getMemoByMemoId(memoId);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "get memo", memoResponseData), HttpStatus.OK);
    }

    // 메모에 좋아요를 누르는 기능
    @PostMapping("/memos/likes/add/{memoId}")
    public ResponseEntity<ResponseDto<MemoResponseData>> addLikeByMemoId(@PathVariable("memoId") UUID memoId, @AuthenticatedUser User user) {
        this.memoService.validateMemoById(memoId);
        this.memoService.addLikeByMemoId(user, memoId);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.CREATED, "add like"), HttpStatus.CREATED);
    }

    // 특정 메모의 좋아요 리스트 반환
    // 메모의 좋아요 갯수와 좋아요를 누른 유저의 이름을 보여준다.
    @GetMapping("/memos/likes/{memoId}")
    public ResponseEntity<ResponseDto<MemoLikeResponseData>> getLikeByMemoId(@PathVariable("memoId") UUID memoId) {
        this.memoService.validateMemoById(memoId);
        MemoLikeResponseData likeListResponseData = memoService.getLikeByMemoId(memoId);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "get like list", likeListResponseData), HttpStatus.OK);
    }

    // 해당 유저가 작성한 메모를 메모 아이디를 통해 삭제
    @DeleteMapping("/memos/remove/{memoId}")
    public ResponseEntity<ResponseDto<Void>> deleteMemoByMemoId(@PathVariable("memoId") UUID memoId, @AuthenticatedUser User user) {
        this.memoService.validateMemoById(memoId);
        this.memoService.isUserHaveAccessTo(user, memoId);

        this.memoService.deleteMemoByMemoId(memoId);

        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "delete memo"), HttpStatus.OK);
    }

    // 해당 유저가 작성한 메모를 메모 아이디를 통해 수정
    @PatchMapping("/memos/update/{memoId}")
    public ResponseEntity<ResponseDto<Void>> updateMemoByMemoId(@PathVariable("memoId") UUID memoId, @RequestBody @Valid MemoUpdateDto memoUpdateDto,
                                                                @AuthenticatedUser User user) {
        this.memoService.validateMemoById(memoId);
        this.memoService.isUserHaveAccessTo(user, memoId);

        this.memoService.updateMemoByMemoId(memoUpdateDto, memoId);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "update memo"), HttpStatus.OK);
    }

}
