package com.alimberdi.flowforge.services.facade;

import com.alimberdi.flowforge.domain.entities.Card;
import com.alimberdi.flowforge.domain.entities.Task;
import com.alimberdi.flowforge.domain.entities.Workspace;
import com.alimberdi.flowforge.domain.enums.CardStatus;
import com.alimberdi.flowforge.exceptions.EntityNotFoundException;
import com.alimberdi.flowforge.mappers.TaskMapper;
import com.alimberdi.flowforge.services.builder.CardBuilder;
import com.alimberdi.flowforge.services.state.CardState;
import com.alimberdi.flowforge.services.state.CardStateResolver;
import com.alimberdi.flowforge.web.card.CardService;
import com.alimberdi.flowforge.web.card.dtos.CardCreationDTO;
import com.alimberdi.flowforge.web.card.dtos.CardDTO;
import com.alimberdi.flowforge.web.card.dtos.CardUpdateDTO;
import com.alimberdi.flowforge.web.task.TaskService;
import com.alimberdi.flowforge.web.task.dtos.TaskDTO;
import com.alimberdi.flowforge.web.task.dtos.TaskUpdateDTO;
import com.alimberdi.flowforge.web.workspace.WorkspaceService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CardFacade {

	private final CardService cardService;
	private final WorkspaceService workspaceService;
	private final TaskService taskService;
	private final CardStateResolver cardStateResolver;

	public CardFacade(CardService cardService, WorkspaceService workspaceService, TaskService taskService, CardStateResolver cardStateResolver) {
		this.cardService = cardService;
		this.workspaceService = workspaceService;
		this.taskService = taskService;
		this.cardStateResolver = cardStateResolver;
	}

	public CardDTO createDefaultCard(Long workspaceId) {
		Workspace workspace = workspaceService.getWorkspaceEntityById(workspaceId);

		Card card = CardBuilder.builder()
				.title("Untitled")
				.description("Type a description...")
				.status(CardStatus.NOT_STARTED)
				.workspace(workspace)
				.tasks(new ArrayList<>())
				.build();

		return cardService.saveCard(card);
	}

	public CardDTO createCard(Long workspaceId, CardCreationDTO dto) {
		Workspace workspace = workspaceService.getWorkspaceEntityById(workspaceId);

		Card card = CardBuilder.builder()
				.title(dto.title())
				.description(dto.description())
				.status(CardStatus.NOT_STARTED)
				.workspace(workspace)
				.tasks(new ArrayList<>())
				.build();

		return cardService.saveCard(card);
	}

	public CardDTO updateCard(Long id, CardUpdateDTO dto) {
		Card card = cardService.getCardEntityById(id);

		card.setTitle(dto.title());
		card.setDescription(dto.description());

		syncTasks(card, dto.tasks());

		return cardService.saveCard(card);
	}

	public void syncTasks(Card card, List<TaskDTO> incoming) {
		List<Task> updated = new ArrayList<>();

		for (TaskDTO dto : incoming) {
			Task task;

			if (dto.id() != null) {
				task = taskService.getTaskEntityById(dto.id());
			} else {
				task = new Task();
				task.setCard(card);
			}

			task.setTitle(dto.title());
			task.setCompleted(dto.completed());
			task.setLastUpdate(LocalDateTime.now());

			syncSubTasks(task, dto.subTasks());

			updated.add(task);
		}

		card.getTasks().clear();
		card.getTasks().addAll(updated);
	}

	public void syncSubTasks(Task parent, List<TaskDTO> incoming) {
		Map<Long, Task> existing = parent.getSubTasks()
				.stream()
				.collect(Collectors.toMap(Task::getId, Function.identity()));
		List<Task> updated = new ArrayList<>();

		for (TaskDTO dto : incoming) {
			Task task;

			if (dto.id() != null) {
				task = existing.get(dto.id());
				if (task == null) {
					throw new EntityNotFoundException("Subtask not found: " + dto.id());
				}
			} else {
				task = new Task();
				task.setParent(parent);
				task.setCard(parent.getCard());
			}

			task.setTitle(dto.title());
			task.setCompleted(dto.completed());
			task.setLastUpdate(LocalDateTime.now());

			syncSubTasks(task, dto.subTasks());

			updated.add(task);
		}

		parent.getSubTasks().clear();
		parent.getSubTasks().addAll(updated);
	}

	public CardDTO startCard(Long cardId) {
		Card card = cardService.getCardEntityById(cardId);
		CardState state = cardStateResolver.resolve(card);

		state.start(card);
		return cardService.saveCard(card);
	}

	public CardDTO completeCard(Long cardId) {
		Card card = cardService.getCardEntityById(cardId);
		CardState state = cardStateResolver.resolve(card);

		state.complete(card);
		return cardService.saveCard(card);
	}

	public CardDTO reopenCard(Long cardId) {
		Card card = cardService.getCardEntityById(cardId);
		CardState state = cardStateResolver.resolve(card);

		state.reopen(card);
		return cardService.saveCard(card);
	}

}
