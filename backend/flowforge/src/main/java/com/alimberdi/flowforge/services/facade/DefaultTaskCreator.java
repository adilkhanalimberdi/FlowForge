package com.alimberdi.flowforge.services.facade;

import com.alimberdi.flowforge.web.task.dtos.TaskDTO;

public interface DefaultTaskCreator {

	TaskDTO createDefaultTask(Long cardId, Long parentId);

}
