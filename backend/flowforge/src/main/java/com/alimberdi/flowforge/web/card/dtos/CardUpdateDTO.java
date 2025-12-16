package com.alimberdi.flowforge.web.card.dtos;

import com.alimberdi.flowforge.web.task.dtos.TaskDTO;

import java.util.List;

public record CardUpdateDTO(
		String title,
		String description,
		List<TaskDTO> tasks
) {}
