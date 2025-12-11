package com.alimberdi.flowforge.mappers;

import com.alimberdi.flowforge.domain.entities.Card;
import com.alimberdi.flowforge.domain.entities.Task;
import com.alimberdi.flowforge.web.task.dtos.TaskCreationDTO;
import com.alimberdi.flowforge.web.task.dtos.TaskDTO;
import com.alimberdi.flowforge.web.task.dtos.TaskUpdateDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TaskMapper {

	public static TaskDTO toDTO(Task task) {
		List<TaskDTO> subTaskDTOs = task.getSubTasks()
				.stream()
				.map(TaskMapper::toDTO)
				.toList();

		return new TaskDTO(
				task.getId(),
				task.getTitle(),
				task.getCompleted(),
				task.getLastUpdate(),
				subTaskDTOs
		);
	}

	public static Task toEntity(TaskCreationDTO dto, Card card, Task parent) {
		return new Task(
				null,
				dto.title(),
				false,
				LocalDateTime.now(),
				card,
				parent,
				new ArrayList<>()
		);
	}

}
