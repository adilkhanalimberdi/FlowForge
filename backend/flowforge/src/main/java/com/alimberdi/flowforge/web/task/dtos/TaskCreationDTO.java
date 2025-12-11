package com.alimberdi.flowforge.web.task.dtos;

public record 	TaskCreationDTO(
		String title,
		Long cardId,
		Long parentId
) {}
