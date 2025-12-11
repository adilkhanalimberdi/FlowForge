package com.alimberdi.flowforge.web.workspace;

import com.alimberdi.flowforge.domain.entities.User;
import com.alimberdi.flowforge.domain.entities.Workspace;
import com.alimberdi.flowforge.exceptions.EntityNotFoundException;
import com.alimberdi.flowforge.mappers.WorkspaceMapper;
import com.alimberdi.flowforge.repositories.UserRepository;
import com.alimberdi.flowforge.repositories.WorkspaceRepository;
import com.alimberdi.flowforge.web.workspace.dtos.WorkspaceCreationDTO;
import com.alimberdi.flowforge.web.workspace.dtos.WorkspaceDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class WorkspaceService {

	private WorkspaceRepository workspaceRepository;
	private UserRepository userRepository;

	public WorkspaceDTO getWorkspaceById(Long id) {
		Workspace workspace = workspaceRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Workspace with id: " + id + " not found"));

		return WorkspaceMapper.toDTO(workspace);
	}

	public Workspace getWorkspaceEntityById(Long id) {
		return workspaceRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Workspace with id: " + id + " not found"));
	}

	public List<WorkspaceDTO> getAllWorkspaces() {
		List<Workspace> workspaces = workspaceRepository.findAll();

		return workspaces
				.stream()
				.map(WorkspaceMapper::toDTO)
				.toList();
	}

	public WorkspaceDTO createWorkspace(WorkspaceCreationDTO dto) {
		User user = userRepository.findById(dto.userId())
				.orElseThrow(() -> new EntityNotFoundException("User with id: " + dto.userId() + " not found"));

		Workspace workspace = new Workspace(
				null,
				dto.title(),
				user,
				new ArrayList<>() // When we create Workspace first time we don't have any Card in it
		);

		return WorkspaceMapper.toDTO(workspaceRepository.save(workspace));
	}

	public WorkspaceDTO updateWorkspace(Long id, WorkspaceCreationDTO dto) {
		Workspace workspace = workspaceRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Workspace with id: " + id + " not found"));

		// We don't need to change User and Cards in the Workspace
		workspace.setTitle(dto.title());

		return WorkspaceMapper.toDTO(workspaceRepository.save(workspace));
	}

	public HttpEntity<String> deleteWorkspace(Long id) {
		workspaceRepository.deleteById(id);
		return new HttpEntity<>("Successful");
	}

}
