package org.mjulikelion.memomanagement.authentication.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mjulikelion.memomanagement.authentication.AuthenticationContext;
import org.mjulikelion.memomanagement.authentication.AuthenticationExtractor;
import org.mjulikelion.memomanagement.authentication.token.JwtTokenProvider;
import org.mjulikelion.memomanagement.errorcode.ErrorCode;
import org.mjulikelion.memomanagement.exception.NotFoundException;
import org.mjulikelion.memomanagement.model.User;
import org.mjulikelion.memomanagement.repository.UserRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthenticationInterceptor implements HandlerInterceptor {
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationContext authenticationContext;
    private final UserRepository userRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String accessToken = AuthenticationExtractor.extractTokenFromRequest(request);
        UUID userId = UUID.fromString(jwtTokenProvider.getPayload(accessToken));
        User user = this.userRepository.findById(userId).orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));
        authenticationContext.setPrincipal(user);
        return true;
    }
}
