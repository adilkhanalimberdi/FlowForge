package com.alimberdi.flowforge.controllers;

import com.alimberdi.flowforge.services.AuthService;
import com.alimberdi.flowforge.web.user.dtos.UserLoginDTO;
import com.alimberdi.flowforge.web.user.dtos.UserRegistrationDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;

	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody UserRegistrationDTO dto) {
		return authService.register(dto);
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody UserLoginDTO dto) {
		return authService.login(dto);
	}

}
