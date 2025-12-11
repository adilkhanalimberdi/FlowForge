package com.alimberdi.flowforge.web.card.dtos;

public record CardCreationDTO(
		String title,
		String description,
		Long workspaceId
) {}
