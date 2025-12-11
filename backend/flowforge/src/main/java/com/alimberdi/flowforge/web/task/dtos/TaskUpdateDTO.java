package com.alimberdi.flowforge.web.task.dtos;

import java.util.List;

public record TaskUpdateDTO(
		Long id,
		String title,
		Boolean completed,
		Long cardId,
		Long parentId,
		List<TaskDTO> subTasks
) {}
