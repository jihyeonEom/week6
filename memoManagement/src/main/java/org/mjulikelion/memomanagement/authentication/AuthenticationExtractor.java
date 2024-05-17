package org.mjulikelion.memomanagement.authentication;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.mjulikelion.memomanagement.authentication.token.JwtEncoder;
import org.mjulikelion.memomanagement.errorcode.ErrorCode;
import org.mjulikelion.memomanagement.exception.UnauthorizedException;

public class AuthenticationExtractor {
    private static final String TOKEN_COOKIE_NAME = "AccessToken";

    public static String extractTokenFromRequest(final HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (TOKEN_COOKIE_NAME.equals(cookie.getName())) {
                    return JwtEncoder.decodeJwtBearerToken(cookie.getValue());
                }
            }
        }
        throw new UnauthorizedException(ErrorCode.TOKEN_NOT_FOUND);
    }
}
