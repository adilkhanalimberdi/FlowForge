package com.alimberdi.flowforge.mappers;

import com.alimberdi.flowforge.domain.entities.Workspace;
import com.alimberdi.flowforge.web.card.dtos.CardDTO;
import com.alimberdi.flowforge.web.workspace.dtos.WorkspaceDTO;

import java.util.List;

public class WorkspaceMapper {

	public static WorkspaceDTO toDTO(Workspace workspace) {
		List<CardDTO> cardDTOs = workspace.getCards()
				.stream()
				.map(CardMapper::toDTO)
				.toList();

		return new WorkspaceDTO(
				workspace.getId(),
				workspace.getTitle(),
				cardDTOs
		);
	}

}
