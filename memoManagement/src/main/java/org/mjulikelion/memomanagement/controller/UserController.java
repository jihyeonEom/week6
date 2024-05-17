package org.mjulikelion.memomanagement.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.mjulikelion.memomanagement.authentication.token.JwtEncoder;
import org.mjulikelion.memomanagement.authentication.token.JwtTokenProvider;
import org.mjulikelion.memomanagement.authentication.user.AuthenticatedUser;
import org.mjulikelion.memomanagement.dto.ResponseDto;
import org.mjulikelion.memomanagement.dto.response.user.UserResponseData;
import org.mjulikelion.memomanagement.dto.user.UserCreateDto;
import org.mjulikelion.memomanagement.dto.user.UserUpdateDto;
import org.mjulikelion.memomanagement.model.User;
import org.mjulikelion.memomanagement.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.UUID;

@RestController
@AllArgsConstructor
public class UserController {
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    // 회원가입
    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ResponseDto<Void>> createUser(@RequestBody @Valid UserCreateDto userCreateDto) {
        this.userService.createUser(userCreateDto);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.CREATED, "create user"), HttpStatus.CREATED);
    }

    // 내 정보 조회하기
    @GetMapping("/users/info")
    public ResponseEntity<ResponseDto<UserResponseData>> getUserInfo(@AuthenticatedUser User user) {
        UserResponseData userResponseData = this.userService.getUserInfo(user);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "user info", userResponseData), HttpStatus.OK);
    }

    // 로그인
    @GetMapping("/users/login")
    public ResponseEntity<ResponseDto<Void>> login(@RequestHeader("userId") UUID userId, HttpServletResponse response) {
        this.userService.validateUserById(userId); // 유저 검증

        String payload = userId.toString();
        String accessToken = jwtTokenProvider.createToken(payload); // 유저 아이디를 담은 토큰 생성
        ResponseCookie cookie = ResponseCookie.from("AccessToken", JwtEncoder.encodeJwtBearerToken(accessToken))
                .maxAge(Duration.ofMillis(1800000))
                .path("/")
                .build();
        response.addHeader("Set-Cookie", cookie.toString());
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "login"), HttpStatus.OK);
    }

    // 회원 탈퇴
    @DeleteMapping("/users")
    public ResponseEntity<ResponseDto<Void>> deleteUser(@AuthenticatedUser User user) {
        this.userService.deleteUser(user);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "delete user"), HttpStatus.OK);
    }

    // 회원 정보 수정
    @PatchMapping("/users")
    public ResponseEntity<ResponseDto<Void>> updateUser(@RequestBody @Valid UserUpdateDto userUpdateDto,
                                                        @AuthenticatedUser User user) {
        this.userService.updateUser(userUpdateDto, user);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "update user"), HttpStatus.OK);
    }

}
