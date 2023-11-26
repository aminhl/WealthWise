package com.nexthope.wealthwise_backend.config;

import com.nexthope.wealthwise_backend.token.Token;
import com.nexthope.wealthwise_backend.token.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {
    private final TokenRepository tokenRepository;

    @Override
    public void logout(HttpServletRequest request,
                       HttpServletResponse response,
                       Authentication authentication) {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer")) return;
        String accessToken = authHeader.substring(7);
        Token token = tokenRepository.findTokenByToken(accessToken)
                .orElseThrow(() -> new IllegalStateException("token not found"));
        if (token != null){
            token.setExpired(true);
            token.setRevoked(true);
            tokenRepository.save(token);
        }
    }
}
