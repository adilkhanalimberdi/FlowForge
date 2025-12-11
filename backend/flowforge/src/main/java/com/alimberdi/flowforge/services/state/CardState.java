package com.alimberdi.flowforge.services.state;

import com.alimberdi.flowforge.domain.entities.Card;
import com.alimberdi.flowforge.domain.enums.CardStatus;

public interface CardState {

	void start(Card card);
	void complete(Card card);
	void reopen(Card card);

	CardStatus getStatus();

}
