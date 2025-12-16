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

	public WorkspaceDTO saveWorkspace(Workspace workspace) {
		return WorkspaceMapper.toDTO(workspaceRepository.save(workspace));
	}

	public WorkspaceDTO updateWorkspace(Long id, String title) {
		Workspace workspace = workspaceRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Workspace with id: " + id + " not found"));

		workspace.setTitle(title);
		return WorkspaceMapper.toDTO(workspaceRepository.save(workspace));
	}

	public HttpEntity<String> deleteWorkspace(Long id) {
		workspaceRepository.deleteById(id);
		return new HttpEntity<>("Successful");
	}

}
