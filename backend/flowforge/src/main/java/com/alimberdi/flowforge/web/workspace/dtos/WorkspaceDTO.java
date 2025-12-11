package com.alimberdi.flowforge.web.workspace.dtos;

import com.alimberdi.flowforge.web.card.dtos.CardDTO;

import java.util.List;

public record WorkspaceDTO(
		Long id,
		String title,
		List<CardDTO> cards
) {}
