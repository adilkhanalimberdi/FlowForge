package com.alimberdi.flowforge.web.user.dtos;

public record UserRegistrationDTO(
		String username,
		String email,
		String password
) {}
