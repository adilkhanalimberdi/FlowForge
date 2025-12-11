package com.alimberdi.flowforge.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "workspaces")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Workspace {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String title = "Untitled";

	@ManyToOne
	private User user;

	@OneToMany(mappedBy = "workspace", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Card> cards;

}
