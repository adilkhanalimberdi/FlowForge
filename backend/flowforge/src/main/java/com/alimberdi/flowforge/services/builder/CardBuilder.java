package com.alimberdi.flowforge.services.builder;

import com.alimberdi.flowforge.domain.entities.Card;
import com.alimberdi.flowforge.domain.entities.Task;
import com.alimberdi.flowforge.domain.entities.Workspace;
import com.alimberdi.flowforge.domain.enums.CardStatus;

import java.util.ArrayList;

public class CardBuilder implements Builder<Card> {

	private Card card;

	public CardBuilder() {
		this.card = new Card();
	}

	public static CardBuilder builder() {
		return new CardBuilder();
	}

	public CardBuilder title(String title) {
		card.setTitle(title);
		return this;
	}

	public CardBuilder description(String description) {
		card.setDescription(description);
		return this;
	}

	public CardBuilder status(CardStatus status) {
		card.setStatus(status);
		return this;
	}

	public CardBuilder workspace(Workspace workspace) {
		card.setWorkspace(workspace);
		return this;
	}

	public CardBuilder tasks(ArrayList<Task> tasks) {
		card.setTasks(tasks);
		return this;
	}

	@Override
	public Card build() {
		Card result = this.card;
		this.card = null;
		return result;
	}
}
