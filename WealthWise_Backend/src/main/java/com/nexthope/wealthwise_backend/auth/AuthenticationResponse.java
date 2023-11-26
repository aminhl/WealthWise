package com.nexthope.wealthwise_backend.auth;

import lombok.Builder;

@Builder
public record AuthenticationResponse(String accessToken) {
}
