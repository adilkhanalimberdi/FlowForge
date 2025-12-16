package com.alimberdi.flowforge.services.builder;

import com.alimberdi.flowforge.domain.entities.Card;
import com.alimberdi.flowforge.domain.entities.User;
import com.alimberdi.flowforge.domain.entities.Workspace;

import java.util.List;

public class WorkspaceBuilder implements Builder<Workspace> {

	private Workspace workspace;

	public WorkspaceBuilder() {
		workspace = new Workspace();
	}

	public static WorkspaceBuilder builder() {
		return new WorkspaceBuilder();
	}

	public WorkspaceBuilder title(String title) {
		workspace.setTitle(title);
		return this;
	}

	public WorkspaceBuilder user(User user) {
		workspace.setUser(user);
		return this;
	}

	public WorkspaceBuilder cards(List<Card> cards) {
		workspace.setCards(cards);
		return this;
	}

	@Override
	public Workspace build() {
		Workspace result = this.workspace;
		this.workspace = null;
		return result;
	}

}
