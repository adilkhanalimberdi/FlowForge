package com.alimberdi.flowforge.mappers;

import com.alimberdi.flowforge.domain.entities.User;
import com.alimberdi.flowforge.web.user.dtos.UserDTO;
import com.alimberdi.flowforge.web.user.dtos.UserRegistrationDTO;
import com.alimberdi.flowforge.web.workspace.dtos.WorkspaceDTO;

import java.util.ArrayList;
import java.util.List;

public class UserMapper {

	public static UserDTO toDTO(User user) {
		List<WorkspaceDTO> workspaceDTOs = user.getWorkspaces()
				.stream()
				.map(WorkspaceMapper::toDTO)
				.toList();

		return new UserDTO(
				user.getId(),
				user.getUsername(),
				user.getEmail(),
				user.getProfilePictureUrl(),
				workspaceDTOs
		);
	}

	public static User toEntity(UserRegistrationDTO dto) {
		return new User(
				null,
				dto.username(),
				dto.email(),
				dto.password(),
				null,
				new ArrayList<>()
		);
	}

}
