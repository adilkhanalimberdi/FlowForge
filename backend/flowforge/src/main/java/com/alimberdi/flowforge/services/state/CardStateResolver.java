package com.alimberdi.flowforge.services.state;

import com.alimberdi.flowforge.domain.entities.Card;
import com.alimberdi.flowforge.domain.enums.CardStatus;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CardStateResolver {

	private final Map<CardStatus, CardState> stateMap = new HashMap<>();

	public CardStateResolver(List<CardState> states) {
		for (CardState state : states) {
			stateMap.put(state.getStatus(), state);
		}
	}

	public CardState resolve(Card card) {
		return stateMap.get(card.getStatus());
	}

}
