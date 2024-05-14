package org.mjulikelion.memomanagement.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UserCreateDto {
    @NotBlank(message = "이메일이 비어있습니다.")
    @Pattern(regexp = "^[A-Za-z0-9]+@[A-Za-z0-9]+.[A-Za-z]{2,6}$",
            message = "이메일 형식이 맞지 않습니다.")
    private String email;

    @NotBlank(message = "비밀번호가 비어있습니다.")
    @Size(min = 4, max = 12, message = "비밀번호는 4자 이상 12자 이하여야 합니다.")
    private String password;

    @NotBlank(message = "이름이 비어있습니다.")
    @Size(min = 2, max = 10, message = "이름은 2자 이상 10자 이하여야 합니다.")
    private String name; // 유저 이름
}
