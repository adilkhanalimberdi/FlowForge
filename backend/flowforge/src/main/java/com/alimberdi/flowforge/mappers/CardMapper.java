package com.alimberdi.flowforge.mappers;

import com.alimberdi.flowforge.domain.entities.Card;
import com.alimberdi.flowforge.domain.entities.Task;
import com.alimberdi.flowforge.domain.entities.Workspace;
import com.alimberdi.flowforge.web.card.dtos.CardCreationDTO;
import com.alimberdi.flowforge.web.card.dtos.CardDTO;
import com.alimberdi.flowforge.web.task.dtos.TaskDTO;

import java.util.List;

public class CardMapper {

	public static CardDTO toDTO(Card card) {
		List<TaskDTO> taskDTOs = card.getTasks()
				.stream()
				.filter(task -> task.getParent() == null)
				.map(TaskMapper::toDTO)
				.toList();

		return new CardDTO(
				card.getId(),
				card.getTitle(),
				card.getDescription(),
				card.getStatus(),
				taskDTOs
		);
	}

}
