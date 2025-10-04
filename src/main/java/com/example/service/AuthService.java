package com.example.service;

import com.example.dto.LoginRequest;
import com.example.dto.LoginResponse;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    public LoginResponse authenticate(LoginRequest request) {
        // Dummy authentication logic
        if("user".equals(request.getUsername()) && "pass".equals(request.getPassword())) {
            return new LoginResponse("dummy-token-123", request.getUsername());
        }
        return new LoginResponse("", "");
    }
}
