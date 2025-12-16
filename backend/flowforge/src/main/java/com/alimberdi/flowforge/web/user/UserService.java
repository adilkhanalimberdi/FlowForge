package com.alimberdi.flowforge.web.user;

import com.alimberdi.flowforge.domain.entities.User;
import com.alimberdi.flowforge.exceptions.EntityNotFoundException;
import com.alimberdi.flowforge.mappers.UserMapper;
import com.alimberdi.flowforge.repositories.UserRepository;
import com.alimberdi.flowforge.web.user.dtos.UserDTO;
import com.alimberdi.flowforge.web.user.dtos.UserRegistrationDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

	private UserRepository userRepository;

	public UserDTO getUserById(Long id) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("User with id: " + id + " not found"));

		return UserMapper.toDTO(user);
	}

	public UserDTO getCurrentUser(Authentication authentication) {
		return UserMapper.toDTO(
				userRepository.findByEmail(authentication.getName())
						.orElseThrow(() -> new EntityNotFoundException("User with email: " + authentication.getName() + " not found")));
	}

	public User getUserEntityById(Long id) {
		return userRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("User with id: " + id + " not found"));
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

		user.setFirstName(dto.firstName());
		user.setLastName(dto.lastName());
		user.setEmail(dto.email());
		user.setPassword(dto.password());

		return UserMapper.toDTO(userRepository.save(user));
	}

	public HttpEntity<String> deleteUserById(Long id) {
		userRepository.deleteById(id);
		return new HttpEntity<>("Successful");
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		return userRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("User with email: " + email + " not found"));
	}
}


