package com.movie.booking.controller;

import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/account")
public class AccountController {

    @GetMapping("/me")
    public Map<String, String> getCurrentUser(Authentication authentication) {

        return Map.of("userId",authentication.getName());
    }
}