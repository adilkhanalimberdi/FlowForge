package com.alimberdi.flowforge.domain.entities;

import com.alimberdi.flowforge.domain.enums.CardStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cards")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Card {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String title;

	@Column
	private String description;

	@Column
	@Enumerated(EnumType.STRING)
	private CardStatus status;

	@ManyToOne
	private Workspace workspace;

	@OneToMany(mappedBy = "card", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Task> tasks;

}
