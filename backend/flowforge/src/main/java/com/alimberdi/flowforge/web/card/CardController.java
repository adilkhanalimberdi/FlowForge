package com.alimberdi.flowforge.web.card;

import com.alimberdi.flowforge.services.facade.CardFacade;
import com.alimberdi.flowforge.web.card.dtos.CardCreationDTO;
import com.alimberdi.flowforge.web.card.dtos.CardDTO;
import com.alimberdi.flowforge.web.card.dtos.CardUpdateDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/cards")
public class CardController {

	private CardService cardService;
	private CardFacade cardFacade;

	@GetMapping
	public List<CardDTO> getAllCards() {
		return cardService.getAllCards();
	}

	@GetMapping("/{id}")
	public CardDTO getCardById(@PathVariable("id") Long id) {
		return cardService.getCardById(id);
	}

	@PostMapping("/{workspaceId}")
	public CardDTO createCard(@PathVariable Long workspaceId, @RequestBody CardCreationDTO dto) {
		return cardFacade.createCard(workspaceId, dto);
	}

	@PostMapping("/update/{id}")
	public CardDTO updateCard(@PathVariable("id") Long id, @RequestBody CardUpdateDTO dto) {
		return cardFacade.updateCard(id, dto);
	}

	@PostMapping("/start/{id}")
	public CardDTO startCard(@PathVariable("id") Long id) {
		return cardFacade.startCard(id);
	}

	@PostMapping("/complete/{id}")
	public CardDTO completeCard(@PathVariable("id") Long id) {
		return cardFacade.completeCard(id);
	}

	@PostMapping("/reopen/{id}")
	public CardDTO reopenCard(@PathVariable("id") Long id) {
		return cardFacade.reopenCard(id);
	}

	@PostMapping("/createDefault/{workspaceId}")
	public CardDTO testCard(@PathVariable Long workspaceId) {
		return cardFacade.createDefaultCard(workspaceId);
	}

	@DeleteMapping("/{id}")
	public HttpEntity<String> deleteCardById(@PathVariable("id") Long id) {
		return cardService.deleteCardById(id);
	}

}
