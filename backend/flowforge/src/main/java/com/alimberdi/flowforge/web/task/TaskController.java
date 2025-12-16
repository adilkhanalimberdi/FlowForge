package com.alimberdi.flowforge.web.task;

import com.alimberdi.flowforge.services.facade.TaskFacade;
import com.alimberdi.flowforge.web.task.dtos.TaskCreationDTO;
import com.alimberdi.flowforge.web.task.dtos.TaskDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/tasks")
public class TaskController {

	private TaskService taskService;
	private TaskFacade taskFacade;

	@GetMapping
	public List<TaskDTO> getAllTasks() {
		return taskService.getAllTasks();
	}

	@GetMapping("/{id}")
	public TaskDTO getTaskById(@PathVariable("id") Long id) {
		return taskService.getTaskById(id);
	}

	@PostMapping("/{cardId}")
	public TaskDTO createTask(@PathVariable Long cardId, @RequestBody TaskCreationDTO dto) {
		return taskFacade.createTask(cardId, dto);
	}

	@PostMapping("/createDefault/{cardId}")
	public TaskDTO createDefaultTask(@PathVariable Long cardId, @RequestParam Long parentId) {
		return taskFacade.createDefaultTask(cardId, parentId);
	}

	@PostMapping("/update/{id}")
	public TaskDTO updateTask(@PathVariable("id") Long id, @RequestBody TaskCreationDTO dto) {
		return taskFacade.updateTask(id, dto);
	}

	@PostMapping("/toggle/{id}")
	public TaskDTO toggleCompletion(@PathVariable("id") Long id) {
		return taskFacade.toggle(id);
	}

	@DeleteMapping("/{id}")
	public HttpEntity<String> deleteTaskById(@PathVariable("id") Long id) {
		return taskService.deleteTaskById(id);
	}

}
