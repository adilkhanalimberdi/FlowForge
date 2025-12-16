package com.alimberdi.flowforge.services.facade;

import com.alimberdi.flowforge.domain.entities.Card;
import com.alimberdi.flowforge.domain.entities.Task;
import com.alimberdi.flowforge.services.builder.TaskBuilder;
import com.alimberdi.flowforge.web.card.CardService;
import com.alimberdi.flowforge.web.task.TaskService;
import com.alimberdi.flowforge.web.task.dtos.TaskCreationDTO;
import com.alimberdi.flowforge.web.task.dtos.TaskDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
public class TaskFacade {

	private final TaskService taskService;
	private final CardService cardService;

	public TaskFacade(TaskService taskService, CardService cardService) {
		this.taskService = taskService;
		this.cardService = cardService;
	}

	public TaskDTO createDefaultTask(Long cardId, Long parentId) {
		Card card = cardService.getCardEntityById(cardId);
		Task parent = (parentId != -1) ? taskService.getTaskEntityById(parentId) : null;

		Task task = TaskBuilder.builder()
				.title("Do something...")
				.completed(false) // When we create task, it is not completed yet
				.lastUpdate(LocalDateTime.now())
				.card(card)
				.parent(parent) // We create high-level task, so it does not have parent task
				.subTasks(new ArrayList<>()) // When we create task, it does not have subtasks yet
				.build();

		return taskService.saveTask(task);
	}

	public TaskDTO createTask(Long cardId, TaskCreationDTO dto) {
		Card card = cardService.getCardEntityById(cardId);
		Task parent = (dto.parentId() != null) ? taskService.getTaskEntityById(dto.parentId()) : null;

		Task task = TaskBuilder.builder()
				.title(dto.title())
				.completed(false) // When we create task, it is not completed yet
				.lastUpdate(LocalDateTime.now())
				.card(card)
				.parent(parent)
				.subTasks(new ArrayList<>()) // When we create task, it does not have subtasks
				.build();

		return taskService.saveTask(task);
	}

	public TaskDTO updateTask(Long id, TaskCreationDTO dto) {
		Task task = taskService.getTaskEntityById(id);
		Task parent = (dto.parentId() != null) ? taskService.getTaskEntityById(dto.parentId()) : null;
		Card card = cardService.getCardEntityById(dto.parentId());

		task.setTitle(dto.title());
		task.setCard(card);
		task.setParent(parent);

		return taskService.saveTask(task);
	}

	public TaskDTO toggle(Long id) {
		Task task = taskService.getTaskEntityById(id);
		task.setCompleted(!task.getCompleted());
		return taskService.saveTask(task);
	}

}
