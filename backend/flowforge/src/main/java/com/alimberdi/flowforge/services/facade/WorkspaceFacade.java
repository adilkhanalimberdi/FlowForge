package com.alimberdi.flowforge.services.facade;

import com.alimberdi.flowforge.domain.entities.User;
import com.alimberdi.flowforge.domain.entities.Workspace;
import com.alimberdi.flowforge.exceptions.EntityNotFoundException;
import com.alimberdi.flowforge.services.builder.WorkspaceBuilder;
import com.alimberdi.flowforge.web.user.UserService;
import com.alimberdi.flowforge.web.workspace.WorkspaceService;
import com.alimberdi.flowforge.web.workspace.dtos.WorkspaceCreationDTO;
import com.alimberdi.flowforge.web.workspace.dtos.WorkspaceDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class WorkspaceFacade {

	private final WorkspaceService workspaceService;
	private final UserService userService;

	public WorkspaceFacade(WorkspaceService workspaceService, UserService userService) {
		this.workspaceService = workspaceService;
		this.userService = userService;
	}

	public WorkspaceDTO createDefault(Long userId) {
		User user = userService.getUserEntityById(userId);

		Workspace workspace = WorkspaceBuilder.builder()
				.title("Untitled Workspace.")
				.user(user)
				.cards(new ArrayList<>())
				.build();

		return workspaceService.saveWorkspace(workspace);
	}

	public WorkspaceDTO createWorkspace(Long userId, WorkspaceCreationDTO dto) {
		User user = userService.getUserEntityById(userId);

		Workspace workspace = WorkspaceBuilder.builder()
				.title(dto.title())
				.user(user)
				.cards(new ArrayList<>())
				.build();

		return workspaceService.saveWorkspace(workspace);
	}

}
