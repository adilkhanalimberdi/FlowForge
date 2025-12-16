package com.alimberdi.flowforge.web.user.dtos;

import com.alimberdi.flowforge.domain.enums.UserRole;
import com.alimberdi.flowforge.web.workspace.dtos.WorkspaceDTO;

import java.util.List;

public record UserDTO(
		Long id,
		String firstName,
		String lastName,
		String email,
		String profilePictureUrl,
		List<WorkspaceDTO> workspaces,
		UserRole role
) {}
