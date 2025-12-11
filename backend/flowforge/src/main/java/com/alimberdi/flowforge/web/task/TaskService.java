package com.alimberdi.flowforge.web.task;

import com.alimberdi.flowforge.domain.entities.Task;
import com.alimberdi.flowforge.exceptions.EntityNotFoundException;
import com.alimberdi.flowforge.mappers.TaskMapper;
import com.alimberdi.flowforge.repositories.TaskRepository;
import com.alimberdi.flowforge.web.task.dtos.TaskDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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
		taskRepository.deleteById(id);
		return new HttpEntity<>("Successful");
	}

}
