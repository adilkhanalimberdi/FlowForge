package com.alimberdi.flowforge.web.user;

import com.alimberdi.flowforge.domain.entities.User;
import com.alimberdi.flowforge.exceptions.EntityNotFoundException;
import com.alimberdi.flowforge.mappers.UserMapper;
import com.alimberdi.flowforge.repositories.UserRepository;
import com.alimberdi.flowforge.web.user.dtos.UserDTO;
import com.alimberdi.flowforge.web.user.dtos.UserRegistrationDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

	private UserRepository userRepository;

	public UserDTO getUserById(Long id) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("User with id: " + id + " not found"));

		return UserMapper.toDTO(user);
	}

	public List<UserDTO> getAllUsers() {
		List<User> users = userRepository.findAll();

		return users
				.stream()
				.map(UserMapper::toDTO)
				.toList();
	}

	public UserDTO createUser(UserRegistrationDTO dto) {
		User user = UserMapper.toEntity(dto);

		return UserMapper.toDTO(userRepository.save(user));
	}

	public UserDTO updateUser(Long id, UserRegistrationDTO dto) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("User with id: " + id + " not found"));

		user.setUsername(dto.username());
		user.setEmail(dto.email());
		user.setPassword(dto.password());

		return UserMapper.toDTO(userRepository.save(user));
	}

	public HttpEntity<String> deleteUserById(Long id) {
		userRepository.deleteById(id);
		return new HttpEntity<>("Successful");
	}

}


