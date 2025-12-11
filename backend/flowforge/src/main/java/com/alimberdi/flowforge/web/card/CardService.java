package com.alimberdi.flowforge.web.card;

import com.alimberdi.flowforge.domain.entities.Card;
import com.alimberdi.flowforge.domain.entities.Workspace;
import com.alimberdi.flowforge.domain.enums.CardStatus;
import com.alimberdi.flowforge.exceptions.EntityNotFoundException;
import com.alimberdi.flowforge.mappers.CardMapper;
import com.alimberdi.flowforge.repositories.CardRepository;
import com.alimberdi.flowforge.repositories.WorkspaceRepository;
import com.alimberdi.flowforge.services.builder.CardBuilder;
import com.alimberdi.flowforge.web.card.dtos.CardCreationDTO;
import com.alimberdi.flowforge.web.card.dtos.CardDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CardService {

	private CardRepository cardRepository;
	private WorkspaceRepository workspaceRepository;

	public List<CardDTO> getAllCards() {
		List<Card> cards = cardRepository.findAll();

		return cards
				.stream()
				.map(CardMapper::toDTO)
				.toList();
	}

	public CardDTO getCardById(Long id) {
		Card card = cardRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Card with id " + id + " not found"));

		return CardMapper.toDTO(card);
	}

	public Card getCardEntityById(Long id) {
		return cardRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Card with id " + id + " not found"));
	}

	public CardDTO saveCard(Card card) {
		return CardMapper.toDTO(cardRepository.save(card));
	}

	public HttpEntity<String> deleteCardById(Long id) {
		cardRepository.deleteById(id);
		return new HttpEntity<>("Successful");
	}

}
