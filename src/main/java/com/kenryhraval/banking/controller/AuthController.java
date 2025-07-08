package com.kenryhraval.banking.controller;

import com.kenryhraval.banking.dto.LoginRequest;
import com.kenryhraval.banking.dto.RegisterRequest;
import com.kenryhraval.banking.model.User;
import com.kenryhraval.banking.security.JwtService;
import com.kenryhraval.banking.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final JwtService jwtService;

    @Autowired
    public AuthController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @Operation(summary = "Log in with username and password", description = "Returns JWT token if login is successful.")
    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {
        User user = userService.authenticate(request.getUsername(), request.getPassword());
        return jwtService.generateToken(user);
    }

    @Operation(summary = "Register a new user", description = "Creates a user with the provided username and password.")
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        userService.registerUser(request.getUsername(), request.getPassword());
        return ResponseEntity.ok("User registered");
    }
}
