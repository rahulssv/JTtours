package com.jt.model.tour;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Itinerary {

	@ElementCollection
	@CollectionTable(name = "daily_plan")
	private List<DayPlan> dayPlans= new ArrayList<DayPlan>();

}
