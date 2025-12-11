package com.alimberdi.flowforge.web.workspace;

import com.alimberdi.flowforge.web.workspace.dtos.WorkspaceCreationDTO;
import com.alimberdi.flowforge.web.workspace.dtos.WorkspaceDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/workspaces")
public class WorkspaceController {

	private WorkspaceService workspaceService;

	@GetMapping
	public List<WorkspaceDTO> getAllWorkspaces() {
		return workspaceService.getAllWorkspaces();
	}

	@GetMapping("/{id}")
	public WorkspaceDTO getWorkspaceById(@PathVariable Long id) {
		return workspaceService.getWorkspaceById(id);
	}

	@PostMapping
	public WorkspaceDTO createWorkspace(@RequestBody WorkspaceCreationDTO dto) {
		return workspaceService.createWorkspace(dto);
	}

	@PostMapping("/{id}")
	public WorkspaceDTO updateWorkspace(@PathVariable Long id, @RequestBody WorkspaceCreationDTO dto) {
		return workspaceService.updateWorkspace(id, dto);
	}

	@DeleteMapping("/{id}")
	public HttpEntity<String> deleteWorkspace(@PathVariable Long id) {
		return workspaceService.deleteWorkspace(id);
	}

}
