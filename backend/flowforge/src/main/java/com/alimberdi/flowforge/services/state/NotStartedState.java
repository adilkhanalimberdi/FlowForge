package com.alimberdi.flowforge.services.state;

import com.alimberdi.flowforge.domain.entities.Card;
import com.alimberdi.flowforge.domain.enums.CardStatus;
import com.alimberdi.flowforge.exceptions.IllegalStateException;
import org.springframework.stereotype.Component;

@Component
public class NotStartedState implements CardState {

	@Override
	public void start(Card card) {
		card.setStatus(CardStatus.IN_PROGRESS);
	}

	@Override
	public void complete(Card card) {
		throw new IllegalStateException("Cannot complete task that is not started");
	}

	@Override
	public void reopen(Card card) {
		throw new IllegalStateException("Task is not started");
	}

	@Override
	public CardStatus getStatus() {
		return CardStatus.NOT_STARTED;
	}

}
