package com.jt.model.batch;

import java.time.LocalDate;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.jt.model.tour.GuidedTour;
import com.jt.model.user.Traveller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="tour_batch")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TourBatch {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY )
	private Long id;
	
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
	private LocalDate startDate;
	
	private LocalDate endDate;
	
	@Enumerated(EnumType.STRING)
	private Status status;
	private int capacity;
	private Double perParticipantCost;
	private int availableSeats;

	@Embedded
	private Traveller guide;
	
	@ManyToOne(targetEntity = GuidedTour.class)
	@JoinColumn(name = "tourId")
	private GuidedTour tour;
	
	public void setEndDate(int duration) {
		this.endDate=getStartDate().plusDays(duration);	
	}
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
	public void setEndDate(LocalDate endDate) {
		this.endDate=endDate;
		
	}
	
	
	@PreUpdate
	@PrePersist
	public void setAvailableSeats() {
		this.availableSeats  = this.capacity;
	}
	
	public void setAvailableSeats(int bookedSeats ) {
		this.availableSeats=this.capacity-bookedSeats;
		if(this.availableSeats<=0) {
			this.status=Status.FULL;
		}
	}
	public void setStatus(String status) {
		this.status=Status.fromString(status);
	}


}
