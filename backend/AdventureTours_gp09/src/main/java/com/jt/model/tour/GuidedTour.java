package com.jt.model.tour;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "guided_tour")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GuidedTour {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true)
	private String name;
	
	@Enumerated(EnumType.STRING)
	private Mode mode;
	
	@Enumerated(EnumType.STRING)
	private DifficultyLevel difficultyLevel;

	@Embedded
	private Itinerary itinerary;
	
	private String startAt;
	private String endAt;

	private int days;
	private int nights;


	public void setMode(String mode) {
		this.mode = Mode.fromString(mode);
	}
	public void setDifficultyLevel(String difficultyLevel) {
		this.difficultyLevel = DifficultyLevel.fromString(difficultyLevel);
	}
	
	@PreUpdate
	 @PrePersist
	 public void setDaysAndNights() {
		 days = itinerary.getDayPlans().size();
		 nights = days - 1;
	}

}
