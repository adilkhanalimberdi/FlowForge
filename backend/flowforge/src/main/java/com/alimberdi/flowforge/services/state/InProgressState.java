package com.alimberdi.flowforge.services.state;

import com.alimberdi.flowforge.domain.entities.Card;
import com.alimberdi.flowforge.domain.enums.CardStatus;
import com.alimberdi.flowforge.exceptions.IllegalStateException;
import org.springframework.stereotype.Component;

@Component
public class InProgressState implements CardState {

	@Override
	public void start(Card card) {
		throw new IllegalStateException("Already started");
	}

	@Override
	public void complete(Card card) {
		card.setStatus(CardStatus.COMPLETED);
	}

	@Override
	public void reopen(Card card) {
		throw new IllegalStateException("Already in progress");
	}

	@Override
	public CardStatus getStatus() {
		return CardStatus.IN_PROGRESS;
	}

}
