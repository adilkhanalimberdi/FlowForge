package com.alimberdi.flowforge.web.task;

import com.alimberdi.flowforge.domain.entities.Task;
import com.alimberdi.flowforge.exceptions.EntityNotFoundException;
import com.alimberdi.flowforge.mappers.TaskMapper;
import com.alimberdi.flowforge.repositories.TaskRepository;
import com.alimberdi.flowforge.services.builder.TaskBuilder;
import com.alimberdi.flowforge.web.task.dtos.TaskDTO;
import com.alimberdi.flowforge.web.task.dtos.TaskUpdateDTO;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class TaskService {

	private TaskRepository taskRepository;

	public List<TaskDTO> getAllTasks() {
		List<Task> tasks = taskRepository.findAll();

		return tasks
				.stream()
				.map(TaskMapper::toDTO)
				.toList();
	}

	public TaskDTO getTaskById(Long id) {
		Task task = taskRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Task with id: " + id + " not found"));

		return TaskMapper.toDTO(task);
	}

	public Task getTaskEntityById(Long id) {
		return taskRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Task with id: " + id + " not found"));
	}

	public TaskDTO saveTask(Task task) {
		return TaskMapper.toDTO(taskRepository.save(task));
	}

	public HttpEntity<String> deleteTaskById(Long id) {
		Task task = taskRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Task with id: " + id + " not found"));

		deleteRecursively(task);
		return new HttpEntity<>("Successful");
	}

	public void deleteRecursively(Task task) {
		for (Task sub : new ArrayList<>(task.getSubTasks())) {
			deleteRecursively(sub);
		}
		taskRepository.delete(task);
	}

}
