package com.alimberdi.flowforge.services;

import com.alimberdi.flowforge.domain.entities.User;
import com.alimberdi.flowforge.domain.enums.UserRole;
import com.alimberdi.flowforge.mappers.UserMapper;
import com.alimberdi.flowforge.repositories.UserRepository;
import com.alimberdi.flowforge.security.JwtUtils;
import com.alimberdi.flowforge.services.builder.UserBuilder;
import com.alimberdi.flowforge.web.user.dtos.UserLoginDTO;
import com.alimberdi.flowforge.web.user.dtos.UserRegistrationDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

	private final AuthenticationManager authenticationManager;
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtUtils jwtUtils;

	public ResponseEntity<?> register(@RequestBody UserRegistrationDTO dto) {
		if (userRepository.findByEmail(dto.email()).isPresent()) {
			return ResponseEntity.badRequest().body(Map.of("error", "email already exists"));
		}

		User user = UserBuilder.builder()
				.email(dto.email())
				.password(passwordEncoder.encode(dto.password()))
				.firstName(dto.firstName())
				.lastName(dto.lastName())
				.role(UserRole.USER)
				.build();

		userRepository.save(user);
		return ResponseEntity.ok(Map.of("message", "successful"));
	}

	public ResponseEntity<?> login(@RequestBody UserLoginDTO dto) {
		try {
			Authentication auth = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(dto.email(), dto.password())
			);

			User user = (User) auth.getPrincipal();
			String token = jwtUtils.generateToken(user.getUsername());
			return ResponseEntity.ok(Map.of(
					"token", token,
					"user", UserMapper.toDTO(user)
			));
		} catch (BadCredentialsException ex) {
			return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
		}
	}

}
