package com.alimberdi.flowforge.web.task.dtos;

import java.time.LocalDateTime;
import java.util.List;

public record TaskDTO(
		Long id,
		String title,
		Boolean completed,
		LocalDateTime lastUpdate,
		List<TaskDTO> subTasks
) {}
