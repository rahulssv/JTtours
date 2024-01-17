package com.jt.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import com.jt.controller.TourControllerTest;
import com.jt.model.batch.TourBatch;
import com.jt.model.tour.DifficultyLevel;
import com.jt.model.tour.GuidedTour;
import com.jt.model.tour.Mode;
import com.jt.repository.BatchRepository;
import com.jt.repository.TourRepository;

import jakarta.persistence.EntityNotFoundException;


@ExtendWith(MockitoExtension.class)
@DisplayName("TourService Testing")
public class TourServiceTest {

	@Mock
	private TourRepository tourRepository;
	
	@Mock
	private BatchRepository tourBatchRepository;

	@InjectMocks
	@Spy
	private TourService tourService;
	
	GuidedTour guidedTour = TourControllerTest.dummyDataForTour();
	
	@Test
	@DisplayName("Testing TourService Create Tour")
	public void createTourTest() {
		Mockito.when(tourRepository.save(guidedTour)).thenReturn(guidedTour);
		GuidedTour guidedTourSaveObject = tourService.addTour(guidedTour);
		assertThat(guidedTourSaveObject).isNotNull();
	}
	@Test
	@DisplayName("Testing TourService Create Tour for Null")
	public void createTourTestForEmptyTour() {
		Mockito.when(tourRepository.save(guidedTour)).thenThrow(new EntityNotFoundException("not saving tour because null"));
		GuidedTour guidedTourSaveObject = tourService.addTour(guidedTour);
		assertThat(guidedTourSaveObject).isNull();
	}
	
	@Test
	@DisplayName("Test tourService GetAll tours")
	public void allToursTest() {
		List<GuidedTour> guidedTourList = new ArrayList<>();
		guidedTourList.add(guidedTour);
		Mockito.when(tourRepository.findAll()).thenReturn(guidedTourList);
		List<GuidedTour> responseList = tourService.getTours();
		assertThat(responseList).isNotNull();
		assertThat(responseList).isNotEmpty();
	}
	@Test
	@DisplayName("Test tourService GetAll tours Empty list")
	public void allToursTestForNull() {
		List<GuidedTour> guidedTourList = new ArrayList<>();
		guidedTourList.add(guidedTour);
		Mockito.when(tourRepository.findAll()).thenThrow(new EntityNotFoundException("Empty list of Tours"));
		List<GuidedTour> responseList = tourService.getTours();
		assertThat(responseList).isNull();
	}
	
	@Test
	@DisplayName("Test Tourservice TourById")
	public void tourByIdTest() {
		Optional<GuidedTour> optional = Optional.of(guidedTour);
		Mockito.when(tourRepository.findById(Mockito.anyLong())).thenReturn(optional);
		GuidedTour responseGuidedTour = tourService.getTourById(1l);
		assertThat(responseGuidedTour).isNotNull();
		assertEquals(responseGuidedTour.getName() , "mumbai-leh");	
	}
	
	@Test
	@DisplayName("Test Tourservice TourById for Invalid id")
	public void tourByIdTestForInvalidId() {
		Mockito.when(tourRepository.findById(Mockito.anyLong())).thenThrow(new EntityNotFoundException("not found with tour id"));
		GuidedTour responseGuidedTour = tourService.getTourById(1l);
		assertThat(responseGuidedTour).isNull();	
	}
	@Test
	@DisplayName("Test deleteById")
	public void deleteTourById() {
		Long tourId = 1L;
		Mockito.doNothing().when(tourRepository).deleteById(tourId);
		boolean result = tourService.deleteTourById(tourId);
		assertTrue(result);
	}
	@Test
	@DisplayName("Test deleteById for Invalid id")
	public void deleteTourByIdForInvalidId() {
		Long tourId = -1L;
		Mockito.doNothing().when(tourRepository).deleteById(0L);
		boolean result = tourService.deleteTourById(tourId);
		assertFalse(result);
	}
	
	@Test
	@DisplayName("Test update tour")
	public void updateTour () {
		Long tourId =1L;
		GuidedTour guidedTourInput = new GuidedTour();
		guidedTourInput.setId(tourId);
		guidedTourInput.setName("nagpur-goa");
		when(tourRepository.findById(tourId)).thenReturn(Optional.of(guidedTourInput));
		when(tourRepository.save(guidedTourInput)).thenReturn(guidedTourInput);
		GuidedTour result = tourService.updateTour(guidedTourInput);
		verify(tourRepository).save(guidedTourInput);
		assertThat(result).isNotNull();
	}
	@Test
	@DisplayName("Test update tour")
	public void updateTourForInvalidTour () {
		Long tourId =1L;
		GuidedTour guidedTourInput = new GuidedTour();
		guidedTourInput.setId(tourId);
		guidedTourInput.setName("nagpur-goa");
		when(tourRepository.findById(tourId)).thenThrow(new EntityNotFoundException("Update failed"));
		when(tourRepository.save(guidedTourInput)).thenReturn(guidedTourInput);
		GuidedTour result = tourService.updateTour(guidedTourInput);
		verify(tourRepository).save(guidedTourInput);
		assertThat(result).isNull();
	}
	@Test
	@DisplayName("Test get all tourbatch for tour")
	public void getTourBatches() {
		Long tourId = 1L;
		TourBatch tourBatch = new TourBatch();
		tourBatch.setId(tourId);
		List<TourBatch>listOfBatch = new ArrayList<>();
		listOfBatch.add(tourBatch);
		when(tourBatchRepository.findAllByTourId(tourId)).thenReturn(listOfBatch);
		List<TourBatch> result = tourService.getTourBatches(tourId);
		assertThat(result).isNotEmpty();
	}
	
	@Test
	@DisplayName("Test get all tourbatch for tour for invalid case")
	public void getTourBatchesForInvalidCase() {
		Long tourId = 1L;
		TourBatch tourBatch = new TourBatch();
		tourBatch.setId(tourId);
		List<TourBatch>listOfBatch = new ArrayList<>();
		listOfBatch.add(tourBatch);
		when(tourBatchRepository.findAllByTourId(tourId)).thenThrow(new EntityNotFoundException("No batch available for tour"));
		List<TourBatch> result = tourService.getTourBatches(tourId);
		assertThat(result).isNull();
	}
	
	@Test
	@DisplayName("filter tour by duration")
	public void filterTourByDuration() {
		int minDays = -1;
		int maxDays  = 10;
		List<GuidedTour> guidedToursList = new ArrayList<>();
		guidedToursList.add(guidedTour);
		Mockito.when(tourRepository.filterTourByDuration(minDays, maxDays)).thenReturn(guidedToursList);
		
		List<GuidedTour> response = tourService.filterTourByDuration(minDays, maxDays);
		assertThat(response).isNotEmpty();
	}
	
	@Test
	@DisplayName("filter tour by duration")
	public void filterTourByDurationForInvalidTest() {
		int minDays = 1;
		int maxDays  = -10;
		List<GuidedTour> guidedToursList = new ArrayList<>();
		guidedToursList.add(guidedTour);		
		List<GuidedTour> response = tourService.filterTourByDuration(minDays, maxDays);
		assertThat(response).isEmpty();
	}
	
	@Test
	@DisplayName("filter tour by Places")
	public void filterTourByPlace() {
		String source = "mumbai";
		String destination  = "leh";
		List<GuidedTour> guidedToursList = new ArrayList<>();
		guidedToursList.add(guidedTour);	
		
		Mockito.when(tourRepository.filterToursByPlaceBetween(source, destination)).thenReturn(guidedToursList);
		
		List<GuidedTour> response = tourService.filteredToursByPlace(source, destination);
		assertThat(response).isNotEmpty();
	}
	@Test
	@DisplayName("filter tour by Places for Null")
	public void filterTourByPlaceForNull() {
		String source = null;
		String destination  = null;
		List<GuidedTour> guidedToursList = new ArrayList<>();
		guidedToursList.add(guidedTour);	
		
		Mockito.when(tourRepository.findAll()).thenReturn(guidedToursList);
		
		List<GuidedTour> response = tourService.filteredToursByPlace(source, destination);
		assertThat(response).isNotEmpty();
	}
	@Test
	@DisplayName("filter tour by Places for Null")
	public void filterTourByPlaceForSourceNull() {
		String source = null;
		String destination  = "leh";
		List<GuidedTour> guidedToursList = new ArrayList<>();
		guidedToursList.add(guidedTour);	
		
		Mockito.when(tourRepository.filterToursByPlaceDestination(destination)).thenReturn(guidedToursList);
		
		List<GuidedTour> response = tourService.filteredToursByPlace(source, destination);
		assertThat(response).isNotEmpty();
	}
	@Test
	@DisplayName("filter tour by Places for Null")
	public void filterTourByPlaceForDestinationNull() {
		String source = "mumbai";
		String destination  = null;
		List<GuidedTour> guidedToursList = new ArrayList<>();
		guidedToursList.add(guidedTour);	
		
		Mockito.when(tourRepository.filterToursByPlaceSource(source)).thenReturn(guidedToursList);
		
		List<GuidedTour> response = tourService.filteredToursByPlace(source, destination);
		assertThat(response).isNotEmpty();
	}
	@Test
	@DisplayName("filter tour by Places for source and destination are equals")
	public void filterTourByPlaceForSourceAndDestinationEqualas() {
		String source = "mumbai";
		String destination  = "mumbai";
		List<GuidedTour> guidedToursList = new ArrayList<>();
		guidedToursList.add(guidedTour);		
		List<GuidedTour> response = tourService.filteredToursByPlace(source, destination);
		assertThat(response).isNull();
	}
	@Test
	@DisplayName("filter tour by Places for source and destination are equals")
	public void filteredToursByModeTest() {
		Mode mode = Mode.BICYCLE;
		List<GuidedTour> guidedToursList = new ArrayList<>();
		guidedToursList.add(guidedTour);	
		Mockito.when(tourRepository.filterToursByMode(mode)).thenReturn(guidedToursList);
		List<GuidedTour> response = tourService.filteredToursByMode(mode);
		assertThat(response).isNotEmpty();
	}
	@Test
	@DisplayName("filter tour by Places for source and destination are equals")
	public void filteredToursByModeTestForNull() {
		Mode mode = null;
		List<GuidedTour> guidedToursList = new ArrayList<>();
		guidedToursList.add(guidedTour);	
		Mockito.when(tourRepository.findAll()).thenReturn(guidedToursList);
		List<GuidedTour> response = tourService.filteredToursByMode(mode);
		assertThat(response).isNotEmpty();
	}
	@Test
	@DisplayName("filter tours by difficulty")
	public void filteredToursByDifficultyLevelTest() {
		DifficultyLevel diff = DifficultyLevel.EASY;
		List<GuidedTour> guidedToursList = new ArrayList<>();
		guidedToursList.add(guidedTour);	
		Mockito.when(tourRepository.filteredToursByDifficultyLevel(diff)).thenReturn(guidedToursList);
		List<GuidedTour> response = tourService.filteredToursByDifficultyLevel(diff);
		assertThat(response).isNotEmpty();
	}
	
	
}
