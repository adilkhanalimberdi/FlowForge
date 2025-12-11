package com.alimberdi.flowforge.services.builder;

import com.alimberdi.flowforge.domain.entities.Card;
import com.alimberdi.flowforge.domain.entities.Task;

import java.time.LocalDateTime;
import java.util.List;

public class TaskBuilder implements Builder<Task> {

	private Task task;

	public TaskBuilder() {
		task = new Task();
	}

	public static TaskBuilder builder() {
		return new TaskBuilder();
	}

	public TaskBuilder title(String title) {
		task.setTitle(title);
		return this;
	}

	public TaskBuilder completed(Boolean completed) {
		task.setCompleted(completed);
		return this;
	}

	public TaskBuilder lastUpdate(LocalDateTime lastUpdate) {
		task.setLastUpdate(lastUpdate);
		return this;
	}

	public TaskBuilder card(Card card) {
		task.setCard(card);
		return this;
	}

	public TaskBuilder parent(Task parent) {
		task.setParent(parent);
		return this;
	}

	public TaskBuilder subTasks(List<Task> subTasks) {
		task.setSubTasks(subTasks);
		return this;
	}

	@Override
	public Task build() {
		Task result = this.task;
		this.task = null;
		return result;
	}

}
