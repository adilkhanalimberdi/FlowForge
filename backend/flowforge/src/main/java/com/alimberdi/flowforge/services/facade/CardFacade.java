package com.alimberdi.flowforge.services.facade;

import com.alimberdi.flowforge.domain.entities.Card;
import com.alimberdi.flowforge.domain.entities.Workspace;
import com.alimberdi.flowforge.domain.enums.CardStatus;
import com.alimberdi.flowforge.services.builder.CardBuilder;
import com.alimberdi.flowforge.services.state.CardState;
import com.alimberdi.flowforge.services.state.CardStateResolver;
import com.alimberdi.flowforge.web.card.CardService;
import com.alimberdi.flowforge.web.card.dtos.CardCreationDTO;
import com.alimberdi.flowforge.web.card.dtos.CardDTO;
import com.alimberdi.flowforge.web.workspace.WorkspaceService;
import org.springframework.stereotype.Service;

@Service
public class CardFacade {

	private final CardService cardService;
	private final WorkspaceService workspaceService;
	private final CardStateResolver cardStateResolver;

	public CardFacade(CardService cardService, WorkspaceService workspaceService, CardStateResolver cardStateResolver) {
		this.cardService = cardService;
		this.workspaceService = workspaceService;
		this.cardStateResolver = cardStateResolver;
	}

	public CardDTO createDefaultCard(Long workspaceId) {
		Workspace workspace = workspaceService.getWorkspaceEntityById(workspaceId);

		Card card = CardBuilder.builder()
				.title("Untitled")
				.status(CardStatus.NOT_STARTED)
				.workspace(workspace)
				.build();

		return cardService.saveCard(card);
	}

	public CardDTO createCard(Long workspaceId, CardCreationDTO dto) {
		Workspace workspace = workspaceService.getWorkspaceEntityById(workspaceId);

		Card card = CardBuilder.builder()
				.title(dto.title())
				.description(dto.description())
				.status(CardStatus.NOT_STARTED)
				.workspace(workspace)
				.build();

		return cardService.saveCard(card);
	}

	public CardDTO updateCard(Long id, CardCreationDTO dto) {
		Card card = cardService.getCardEntityById(id);
		Workspace workspace = workspaceService.getWorkspaceEntityById(dto.workspaceId());

		card.setTitle(dto.title());
		card.setDescription(dto.description());
		card.setWorkspace(workspace);

		return cardService.saveCard(card);
	}

	public CardDTO startCard(Long cardId) {
		Card card = cardService.getCardEntityById(cardId);
		CardState state = cardStateResolver.resolve(card);

		state.start(card);
		return cardService.saveCard(card);
	}

	public CardDTO completeCard(Long cardId) {
		Card card = cardService.getCardEntityById(cardId);
		CardState state = cardStateResolver.resolve(card);

		state.complete(card);
		return cardService.saveCard(card);
	}

	public CardDTO reopenCard(Long cardId) {
		Card card = cardService.getCardEntityById(cardId);
		CardState state = cardStateResolver.resolve(card);

		state.reopen(card);
		return cardService.saveCard(card);
	}

}
