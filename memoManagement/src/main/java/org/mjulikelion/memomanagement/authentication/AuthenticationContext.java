package org.mjulikelion.memomanagement.authentication;

import lombok.Getter;
import lombok.Setter;
import org.mjulikelion.memomanagement.model.User;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Getter
@Setter
@Component
@RequestScope
public class AuthenticationContext {
    private User principal;
}
