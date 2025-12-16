package com.alimberdi.flowforge.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "tasks")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Task {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String title;

	@Column
	@Builder.Default
	private Boolean completed = false;

	@Column(name = "last_update")
	@Builder.Default
	private LocalDateTime lastUpdate = LocalDateTime.now();
	
	@ManyToOne
	private Card card;

	@ManyToOne
	private Task parent;

	@Column
	@OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Task> subTasks;

}
