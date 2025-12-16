package com.alimberdi.flowforge.services.facade;

import com.alimberdi.flowforge.web.card.dtos.CardDTO;

public interface DefaultCardCreator {

	CardDTO createDefaultCard(Long workspaceId);

}
