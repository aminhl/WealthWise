package com.nexthope.wealthwise_backend.auth;

import java.util.Date;

public record RegisterRequest(String firstname,
                              String lastname,
                              String email,
                              String password,
                              Date dateOfBirth,
                              String image,
                              String address,
                              String phoneNumber) {
}
