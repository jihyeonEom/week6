package org.mjulikelion.memomanagement.errorcode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    // 401 Unauthorized
    UNAUTHORIZED_USER("4010", "Unauthorized user"),
    INVALID_TOKEN("4011", "invalid token"),

    // 404 Not found
    USER_NOT_FOUND("4040", "user not found"),
    MEMO_NOT_FOUND("4041", "memo not found"),
    ORGANIZATION_NOT_FOUND("4042", "organization not found"),
    USER_ORGANIZATION_NOT_FOUND("4043", "user not in organization"),
    TOKEN_NOT_FOUND("4044", "token not found"),

    // 409 Conflict
    EMAIL_ALREADY_EXISTS("4090", "email already exists"),
    USER_ORGANIZATION_ALREADY_EXISTS("4091", "user already join organization"),

    // 403 Forbidden
    USER_DOES_NOT_HAVE_ACCESS("4030", "user does not have access"),

    NOT_NULL("9001", "required value is null"),
    NOT_BLANK("9002", "required value is null or empty"),
    NOT_EMAIL("9002", "required value is not email pattern"),
    NOT_SIZE("9003", "required value is not fit in size");

    private final String code;
    private final String message;

    //Dto의 어노테이션을 통해 발생한 에러코드를 반환
    public static ErrorCode resolveValidationErrorCode(String code) {
        return switch (code) {
            case "NotNull" -> NOT_NULL;
            case "NotBlank" -> NOT_BLANK;
            case "Pattern" -> NOT_EMAIL;
            case "Size" -> NOT_SIZE;

            default -> throw new IllegalArgumentException("Unexpected value: " + code);
        };
    }
}
