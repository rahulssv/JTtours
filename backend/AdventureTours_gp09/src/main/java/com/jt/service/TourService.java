package com.jt.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jt.model.batch.TourBatch;
import com.jt.model.tour.DifficultyLevel;
import com.jt.model.tour.GuidedTour;
import com.jt.model.tour.Mode;
import com.jt.repository.TourRepository;
import com.jt.repository.BatchRepository;

@Service
public class TourService {
	@Autowired
	private TourRepository guidedTourRepository;
	GuidedTour tour;
	@Autowired
	private BatchRepository tourBatchRepository;
	
	public List<GuidedTour> filterTourByDuration(int minDays, int maxDays) {
		if ((minDays == -1 && maxDays == Integer.MAX_VALUE) || (minDays > maxDays))
			return Collections.emptyList();
		return guidedTourRepository.filterTourByDuration(minDays, maxDays);
	}

	public GuidedTour addTour(GuidedTour tour) {
		try {
			tour.setDays(tour.getItinerary().getDayPlans().size());
			guidedTourRepository.save(tour);

		} catch (Exception e) {
			return null;
		}
		return tour;
	}

	public List<GuidedTour> getTours() {
		try {
			return (List<GuidedTour>) guidedTourRepository.findAll();
		} catch (Exception e) {
			return null;
		}
	}
	
	public GuidedTour getTourById(Long id) {
		try {
			Optional<GuidedTour> fetchedTour = this.guidedTourRepository.findById(id);
			if(fetchedTour.isPresent()) {
				return fetchedTour.get();
			}
			return null;
		} catch (Exception e) {
			return null;
		}
	}

	public Boolean deleteTourById(Long id) {
		try {
			this.guidedTourRepository.deleteById(id);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public GuidedTour updateTour(GuidedTour updateTour) {

		try {
			this.guidedTourRepository.save(updateTour);
			return getTourById(updateTour.getId());
		} catch (Exception e) {
			return null;
		}

	}

	public List<TourBatch> getTourBatches(Long tourId) {

		try {
			return this.tourBatchRepository.findAllByTourId(tourId);

		} catch (Exception e) {
			return null;
		}

	}
	
	public List<GuidedTour> filteredToursByPlace(String source, String destination) {
		 
		if (source == null && destination == null)
			return (List<GuidedTour>) guidedTourRepository.findAll();
		if (source != null) {
			if (destination != null) {
				if (source.equals(destination))
					return null;
				return guidedTourRepository.filterToursByPlaceBetween(source, destination);
			}
			return guidedTourRepository.filterToursByPlaceSource(source);
		}
		return guidedTourRepository.filterToursByPlaceDestination(destination);
	}
	
	public List<GuidedTour> filteredToursByMode(Mode mode) {
 
		if (mode == null)
			return (List<GuidedTour>) guidedTourRepository.findAll();
 
		return guidedTourRepository.filterToursByMode(mode);
	}
	
	public List<GuidedTour> filteredToursByDifficultyLevel(DifficultyLevel difficultyLevel) {
 
		if (difficultyLevel == null)
			return (List<GuidedTour>) guidedTourRepository.findAll();
 
		return guidedTourRepository.filteredToursByDifficultyLevel(difficultyLevel);
	}

	public List<GuidedTour> getMostPopularTours() {
		try {
			return guidedTourRepository.findMostPopularTours();
		}
		catch(Exception e) {
			return null;
		}
	}
}
