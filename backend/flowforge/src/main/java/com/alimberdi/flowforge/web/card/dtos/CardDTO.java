package com.alimberdi.flowforge.web.card.dtos;

import com.alimberdi.flowforge.domain.enums.CardStatus;
import com.alimberdi.flowforge.web.task.dtos.TaskDTO;

import java.util.List;

public record CardDTO(
		Long id,
		String title,
		String description,
		CardStatus status,
		List<TaskDTO> tasks
) {}
