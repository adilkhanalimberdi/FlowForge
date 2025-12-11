package com.alimberdi.flowforge.services.state;

import com.alimberdi.flowforge.domain.entities.Card;
import com.alimberdi.flowforge.domain.enums.CardStatus;
import com.alimberdi.flowforge.exceptions.IllegalStateException;
import org.springframework.stereotype.Component;

@Component
public class CompletedState implements CardState {

	@Override
	public void start(Card card) {
		throw new IllegalStateException("Task is completed");
	}

	@Override
	public void complete(Card card) {
		throw new IllegalStateException("Already completed");
	}

	@Override
	public void reopen(Card card) {
		card.setStatus(CardStatus.IN_PROGRESS);
	}

	@Override
	public CardStatus getStatus() {
		return CardStatus.COMPLETED;
	}

}
