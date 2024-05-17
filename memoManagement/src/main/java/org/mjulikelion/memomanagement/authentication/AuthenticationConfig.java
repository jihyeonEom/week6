package org.mjulikelion.memomanagement.authentication;

import lombok.RequiredArgsConstructor;
import org.mjulikelion.memomanagement.authentication.interceptor.AuthenticationInterceptor;
import org.mjulikelion.memomanagement.authentication.user.AuthenticatedUserArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class AuthenticationConfig implements WebMvcConfigurer {

    private final AuthenticationInterceptor authenticationInterceptor;
    private final AuthenticatedUserArgumentResolver authenticatedUserArgumentResolver;

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(authenticationInterceptor)
                .addPathPatterns("/users/info")
                .addPathPatterns("/users/leave")
                .addPathPatterns("/users/update")
                .addPathPatterns("/organizations/join/{orgId}")
                .addPathPatterns("/organizations/leave/{orgId}")
                .addPathPatterns("/memos")
                .addPathPatterns("/memos/remove/{memoId}")
                .addPathPatterns("/memos/update/{memoId}")
                .addPathPatterns("/memos/likes/add/{memoId}");
    }

    @Override
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(authenticatedUserArgumentResolver);
    }
}
