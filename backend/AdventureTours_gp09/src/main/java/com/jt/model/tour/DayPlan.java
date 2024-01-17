package com.jt.model.tour;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DayPlan {

	private int dayCount;
	private String place;
	private double distance;
	private String activity;
	
	@Embedded
	private Accomodation accomodation;


}
