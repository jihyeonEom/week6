package org.mjulikelion.memomanagement.authentication.token;

import lombok.Getter;
import org.mjulikelion.memomanagement.errorcode.ErrorCode;
import org.mjulikelion.memomanagement.exception.UnauthorizedException;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
@Getter
public class JwtEncoder {
    private static final String TOKEN_TYPE = "Bearer ";

    public static String decodeJwtBearerToken(final String token) {
        // 인코딩할 때 공백이 '+'가 됐기 때문에 다시 공백으로 바꿔준다.
        String decodedToken = token.replace("+", " ");

        if (decodedToken.startsWith(TOKEN_TYPE)) {
            return decodedToken.substring(TOKEN_TYPE.length());
        }
        throw new UnauthorizedException(ErrorCode.TOKEN_NOT_FOUND);
    }

    public static String encodeJwtBearerToken(final String accessToken) {
        // 공백을 인코딩하면 '+'가 된다.
        return URLEncoder.encode("Bearer " + accessToken, StandardCharsets.UTF_8);
    }
}
