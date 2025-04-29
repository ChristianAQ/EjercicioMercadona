package com.mercadona.eanservice.controller;

import com.mercadona.eanservice.dto.TokenResponseDTO;
import com.mercadona.eanservice.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDTO> login(@RequestParam String username) {
        String token = jwtUtil.generateToken(username);
        return ResponseEntity.ok(new TokenResponseDTO(token));
    }
}
