package com.alimberdi.flowforge.web.user.dtos;

import com.alimberdi.flowforge.web.workspace.dtos.WorkspaceDTO;

import java.util.List;

public record UserDTO(
		Long id,
		String username,
		String email,
		String profilePictureUrl,
		List<WorkspaceDTO> workspaces
) {}
