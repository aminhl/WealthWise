package com.nexthope.wealthwise_backend.auth;

import com.nexthope.wealthwise_backend.config.JwtService;
import com.nexthope.wealthwise_backend.token.Token;
import com.nexthope.wealthwise_backend.token.TokenRepository;
import com.nexthope.wealthwise_backend.token.TokenType;
import com.nexthope.wealthwise_backend.user.Role;
import com.nexthope.wealthwise_backend.user.User;
import com.nexthope.wealthwise_backend.user.UserRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final TokenRepository tokenRepository;

    public AuthenticationResponse register(RegisterRequest request) {
        var user = User
                .builder()
                .firstname(request.firstname())
                .lastname(request.lastname())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(Role.USER)
                .dateOfBirth(request.dateOfBirth())
                .image(request.image())
                .address(request.address())
                .phoneNumber(request.phoneNumber())
                .createdAt(LocalDateTime.now())
                .isActive(true)
                .isTwoFactorAuthEnabled(false)
                .lastLogin(null)
                .lastModifiedAt(null)
                .build();
        String accessToken = jwtService.generateToken(user);
        saveUserToken(user, accessToken);
        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .build();
    }

    private void saveUserToken(User user, String accessToken) {
        userRepository.save(user);
        var token = Token.builder()
                .token(accessToken)
                .tokenType(TokenType.BEARER)
                .isExpired(false)
                .isRevoked(false)
                .user(user)
                .build();
        tokenRepository.save(token);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );
        var user = userRepository.findUserByEmail(request.email())
                .orElseThrow(() -> new UsernameNotFoundException("user " + request.email() + " not found"));
        revokeAllUserValidTokens(user);
        String accessToken = jwtService.generateToken(user);
        saveUserToken(user, accessToken);
        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .build();
    }

    private void revokeAllUserValidTokens(User user) {
        List<Token> userValidTokens = tokenRepository.findAllValidTokensByUser(user.getId());
        userValidTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(userValidTokens);
    }
}
