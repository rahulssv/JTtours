package com.jt.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jt.config.UserInfoUserDetails;
import com.jt.model.batch.TourBatch;
import com.jt.model.tour.DifficultyLevel;
import com.jt.model.tour.GuidedTour;
import com.jt.model.tour.Mode;
import com.jt.service.BatchService;
import com.jt.service.TourService;

@RequestMapping("/tours")
@CrossOrigin(origins = "*")
@RestController
public class TourController {
	@Autowired
	private TourService tourService;
	@Autowired
	private BatchService batchService;
	
	@GetMapping("/day")
	public ResponseEntity<List<GuidedTour>> filterTourByDuration(
			@RequestParam(value = "minDays", required=false, defaultValue="-1" ) int minDays,
			@RequestParam(value = "maxDays", required=false, defaultValue=""+Integer.MAX_VALUE) int maxDays) {
		
		List<GuidedTour> guidedTour=tourService.filterTourByDuration(minDays,maxDays);
		if(guidedTour==null)
			return new ResponseEntity<List<GuidedTour>>(Collections.emptyList(),HttpStatus.NOT_ACCEPTABLE);
		if(guidedTour.isEmpty())
			return new ResponseEntity<List<GuidedTour>>(Collections.emptyList(),HttpStatus.NOT_FOUND);
	
		return new ResponseEntity<List<GuidedTour>>(guidedTour,HttpStatus.OK);
		
	}
	

	
	@GetMapping("/place")
	public ResponseEntity<List<GuidedTour>> filteredToursByPlace(
			@RequestParam(value = "source", required = false) String source,
			@RequestParam(value = "destination", required = false) String destination) {
		List<GuidedTour> guidedTour = tourService.filteredToursByPlace(source, destination);
		
		if(guidedTour==null)
			return new ResponseEntity<List<GuidedTour>>(Collections.emptyList(),HttpStatus.NOT_ACCEPTABLE);
		if(guidedTour.isEmpty())
			return new ResponseEntity<List<GuidedTour>>(Collections.emptyList(),HttpStatus.NOT_FOUND);
	
		return new ResponseEntity<List<GuidedTour>>(guidedTour,HttpStatus.OK);
	}
	
	@GetMapping("/mode")
	public ResponseEntity<List<GuidedTour>> filteredToursByMode(
			@RequestParam(value = "mode", required = false) String mode) {
		List<GuidedTour> guidedTour = tourService.filteredToursByMode(Mode.fromString(mode));
		if(guidedTour==null)
			return new ResponseEntity<List<GuidedTour>>(Collections.emptyList(),HttpStatus.NOT_ACCEPTABLE);
		if(guidedTour.isEmpty())
			return new ResponseEntity<List<GuidedTour>>(Collections.emptyList(),HttpStatus.NOT_FOUND);
	
		return new ResponseEntity<List<GuidedTour>>(guidedTour,HttpStatus.OK);
	}
	
	@GetMapping("/difficultyLevel")
	public ResponseEntity<List<GuidedTour>> filteredToursByDifficultyLevel(
			@RequestParam(value = "difficultyLevel", required = false) String difficultyLevel) {
		List<GuidedTour> guidedTour = tourService.filteredToursByDifficultyLevel(DifficultyLevel.fromString(difficultyLevel));
		if(guidedTour==null)
			return new ResponseEntity<List<GuidedTour>>(Collections.emptyList(),HttpStatus.NOT_ACCEPTABLE);
		if(guidedTour.isEmpty())
			return new ResponseEntity<List<GuidedTour>>(Collections.emptyList(),HttpStatus.NOT_FOUND);
	
		return new ResponseEntity<List<GuidedTour>>(guidedTour,HttpStatus.OK);
	}
	
	
	@GetMapping("/popular")
	public ResponseEntity<List<GuidedTour>> getMostPopularTours() {
	    List<GuidedTour> tours = tourService.getMostPopularTours();
	    if (tours != null) {
	        return new ResponseEntity<>(tours, HttpStatus.OK);
	    }
	    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	
	
	@PostMapping()
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<GuidedTour> saveTour(@RequestBody GuidedTour tour) {
		
		System.out.println("inside tour save tour");
		if (tourService.addTour(tour) != null) {
			return new ResponseEntity<GuidedTour>(tour, HttpStatus.CREATED);
		}
		return new ResponseEntity<GuidedTour>(HttpStatus.NOT_ACCEPTABLE);
	}
	@GetMapping()
	public ResponseEntity<List<GuidedTour>> getTours() {
		List<GuidedTour> tours = tourService.getTours();
		if (tours != null)
			return new ResponseEntity<List<GuidedTour>>(tours, HttpStatus.OK);
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@GetMapping("/{id}")
	public ResponseEntity<GuidedTour> getTourById(@PathVariable Long id) {
		GuidedTour tour = tourService.getTourById(id);
		if (tour != null)
			return new ResponseEntity<GuidedTour>(tour, HttpStatus.OK);
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@GetMapping("/{tourId}/batches")
	public ResponseEntity<List<TourBatch>> getBatchesByTourId(@PathVariable Long tourId , @AuthenticationPrincipal UserInfoUserDetails userDetails) {
		
		if(userDetails!=null && userDetails.getAuthorities().stream()
				.anyMatch(a -> a.getAuthority().equals("ADMIN"))) {
			
			List<TourBatch> tourBatches = batchService.getBatchesByTourId(tourId);
			if (tourBatches != null)
				return new ResponseEntity<List<TourBatch>>(tourBatches, HttpStatus.OK);
			return new ResponseEntity<List<TourBatch>>(HttpStatus.NOT_FOUND);
		}
		else {
			List<TourBatch> tourBatches = batchService.getBatchesByTourIdByUser(tourId);
			if (tourBatches != null)
				return new ResponseEntity<List<TourBatch>>(tourBatches, HttpStatus.OK);
			return new ResponseEntity<List<TourBatch>>(HttpStatus.NOT_FOUND);
		}
	}
	
	
	
	@PutMapping()
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<GuidedTour> updateTour(@RequestBody GuidedTour tour) {
		GuidedTour guidedTour=this.tourService.updateTour(tour);
		if(guidedTour!=null) {
			return new ResponseEntity<GuidedTour>(guidedTour,HttpStatus.OK);
		}
		return new ResponseEntity<GuidedTour>(HttpStatus.NOT_MODIFIED);
		 
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<HttpStatus> deleteTour(@PathVariable Long id) {
		if (tourService.deleteTourById(id) == true) {
			return new ResponseEntity<HttpStatus>(HttpStatus.OK);
		}
		return new ResponseEntity<HttpStatus>(HttpStatus.NOT_ACCEPTABLE); 
	}

	
}
