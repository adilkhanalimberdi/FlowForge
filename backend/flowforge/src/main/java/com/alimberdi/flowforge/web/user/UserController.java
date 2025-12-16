package com.alimberdi.flowforge.web.user;

import com.alimberdi.flowforge.web.user.dtos.UserDTO;
import com.alimberdi.flowforge.web.user.dtos.UserRegistrationDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {

	private UserService userService;

	@GetMapping
	public List<UserDTO> getAllUsers() {
		return userService.getAllUsers();
	}

	@GetMapping("/{id}")
	public UserDTO getUserById(@PathVariable Long id) {
		return userService.getUserById(id);
	}

	@GetMapping("/me")
	public UserDTO getCurrentUser(Authentication authentication) {
		return userService.getCurrentUser(authentication);
	}

	@PostMapping
	public UserDTO createUser(@RequestBody UserRegistrationDTO dto) {
		return userService.createUser(dto);
	}

	@PostMapping("/{id}")
	public UserDTO updateUser(@PathVariable Long id, @RequestBody UserRegistrationDTO dto) {
		return userService.updateUser(id, dto);
	}

	@DeleteMapping("/{id}")
	public HttpEntity<String> deleteUser(@PathVariable Long id) {
		return userService.deleteUserById(id);
	}

}

