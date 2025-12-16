package com.alimberdi.flowforge.web.workspace;

import com.alimberdi.flowforge.services.facade.WorkspaceFacade;
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
	private WorkspaceFacade workspaceFacade;

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
		return workspaceFacade.createWorkspace(dto.userId(), dto);
	}

	@PutMapping("/updateTitle/{id}")
	public WorkspaceDTO updateTitle(@PathVariable Long id, @RequestParam String title) {
		return workspaceService.updateWorkspace(id, title);
	}

	@PostMapping("/createDefault/{id}")
	public WorkspaceDTO createDefault(@PathVariable("id") Long userId) {
		return workspaceFacade.createDefault(userId);
	}

	@DeleteMapping("/{id}")
	public HttpEntity<String> deleteWorkspace(@PathVariable Long id) {
		return workspaceService.deleteWorkspace(id);
	}

}
