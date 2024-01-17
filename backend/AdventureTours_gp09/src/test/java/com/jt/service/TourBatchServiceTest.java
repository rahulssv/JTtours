//package com.jt.service;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.mockito.Mockito.when;
//
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.Optional;
//
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import com.jt.controller.BatchControllerTest;
//import com.jt.controller.TourControllerTest;
//import com.jt.model.batch.Status;
//import com.jt.model.batch.TourBatch;
//import com.jt.model.tour.GuidedTour;
//import com.jt.repository.TourRepository;
//
//import jakarta.persistence.EntityNotFoundException;
//
//import com.jt.repository.BatchRepository;
//
//
//@ExtendWith(MockitoExtension.class)
//@DisplayName("TourBatchService Testing")
//public class TourBatchServiceTest {
//	
//	@Mock
//	private BatchRepository batchRepository;
//	
//	@Mock
//	private TourRepository guidedTourRepository;
//	
//	@InjectMocks
//	private BatchService tourBatchServiceImpl;
//	
//	TourBatch tourBatch = BatchControllerTest.getDummyDataTourBatch();
//	TourBatch tourBatch2 = TourControllerTest.dummyDataForBatch();
//	GuidedTour guidedTour = TourControllerTest.dummyDataForTour();
//	
//	@Test
//	@DisplayName("Adding Tour Batch")
//	public void addBatchTest() throws Exception{
//		Mockito.when(batchRepository.save(tourBatch)).thenReturn(tourBatch);
//		Mockito.when(guidedTourRepository.findById(1l)).thenReturn(Optional.of(guidedTour));
//		tourBatch.setEndDate(guidedTour.getDays());
//		int bookedSeats = 0;
//		tourBatch.setAvailableSeats(bookedSeats);
//		tourBatch.setStatus("Active");
//		tourBatch.getGuide().setBookingSequence(0);;
//		System.out.println("tourbatchresponse "+tourBatch);
//		TourBatch tourBatchResponse = tourBatchServiceImpl.addBatch(1L, tourBatch);
//		System.out.println("tourbatchresponse "+tourBatchResponse);
//		assertThat(tourBatchResponse).isNotNull();
//		
//	}
//	@Test
//	@DisplayName("Adding Tour Batch for empty batch object")
//	public void addBatchTestNotNull() throws Exception{
//		Mockito.when(batchRepository.save(tourBatch)).thenThrow(new EntityNotFoundException("Batch Object is empty"));
//		Mockito.when(guidedTourRepository.findById(1l)).thenReturn(Optional.of(guidedTour));
//		TourBatch tourBatchResponse = tourBatchServiceImpl.addBatch(1L, tourBatch);
//		assertThat(tourBatchResponse).isNull();
//		
//	}
//	@Test
//	@DisplayName("Get tour Batches")
//	public void getBatches()throws Exception {
//		List<TourBatch> listOfBatches = new ArrayList<>();
//		listOfBatches.add(tourBatch);
//		Mockito.when(batchRepository.findAll()).thenReturn(listOfBatches);
//		List<TourBatch> response = tourBatchServiceImpl.getBatches();
//		assertThat(response).isNotEmpty();
//	}
//	@Test
//	@DisplayName("Get tour Batches for invalid batches")
//	public void getBatchesForInvalidTest()throws Exception {
//		List<TourBatch> listOfBatches = new ArrayList<>();
//		listOfBatches.add(tourBatch);
//		Mockito.when(batchRepository.findAll()).thenThrow(new EntityNotFoundException("no object found for batches"));
//		List<TourBatch> response = tourBatchServiceImpl.getBatches();
//		assertThat(response).isNull();
//	}
//
//	@Test
//	@DisplayName("Get Batch By Id")
//	public void getBatchById() {
//		Mockito.when(batchRepository.findById(1l)).thenReturn(Optional.of(tourBatch));
//		TourBatch response = tourBatchServiceImpl.getBatchById(1l);
//		assertThat(response).isNotNull();
//		assertEquals(response.getCapacity(), 55);
//
//	}
//	@Test
//	@DisplayName("Get Batch By Id for Invalid id")
//	public void getBatchByIdForInvalidId() {
//		Mockito.when(batchRepository.findById(1l)).thenReturn(Optional.empty());
//		TourBatch response = tourBatchServiceImpl.getBatchById(1l);
//		assertThat(response).isNull();
//
//	}
//	@Test
//	@DisplayName("Delete Batch by id")
//	public void deleteBatchById() throws Exception{
//		Mockito.when(batchRepository.findById(1l)).thenReturn(Optional.of(tourBatch));
//		Mockito.when(batchRepository.save(tourBatch)).thenReturn(tourBatch);
//		boolean response = tourBatchServiceImpl.deleteBatchById(1l);
//		assertTrue(response);
//	}
//	@Test()
//	@DisplayName("Delete Batch by id for Invalid Id")
//	public void deleteBatchByIdForInvalidId() {
//		when(batchRepository.findById(1l)).thenReturn(Optional.empty());
//		boolean response = tourBatchServiceImpl.deleteBatchById(1l);
//		assertFalse(response);	
//	}
//	
//	@Test
//	@DisplayName("Update Batch")
//	public void updateBatch()throws Exception {
//
//		tourBatch2.setCapacity(55);
//		Mockito.when(batchRepository.findById(1l)).thenReturn(Optional.of(tourBatch2));
//		Mockito.when(batchRepository.save(tourBatch2)).thenReturn(tourBatch2);
//		tourBatch.setAvailableSeats(tourBatch2.getCapacity() - tourBatch2.getAvailableSeats());
//		TourBatch tourBatchResponse = tourBatchServiceImpl.updateBatch(tourBatch2);
//		System.out.println(tourBatchResponse);
//		assertThat(tourBatchResponse).isNotNull();
//		assertThat(tourBatchResponse.getCapacity()).isEqualTo(55);
//		
//	}
//	
//	@Test
//	@DisplayName("Test Filter")
//	public void getFilterBatches() throws ParseException {
//		String min_String= "26-09-2023";
//		String max_String = "30-09-2023";
//	    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");      
//	    Date minDate = formatter.parse(min_String); 
//	    Date maxDate = formatter.parse(max_String);
//	    List<TourBatch> listOfTourBatch = new ArrayList<>();
//	    listOfTourBatch.add(tourBatch);
////	    when(batchRepository.findBatchesBetween(minDate, maxDate)).thenReturn(listOfTourBatch);
////		List<TourBatch> result = tourBatchServiceImpl.getFilteredBatches(minDate, maxDate);
////		assertThat(result).isNotEmpty();
//				
//	}
//	
//	@Test
//	@DisplayName("Test Filter")
//	public void getFilterBatchesforMinDateMaxDateInvalid() throws ParseException {
//		String min_String= "30-09-2023";
//		String max_String = "26-09-2023";
//	    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");      
//	    Date minDate = formatter.parse(min_String); 
//	    Date maxDate = formatter.parse(max_String);
//	    List<TourBatch> listOfTourBatch = new ArrayList<>();
//	    listOfTourBatch.add(tourBatch);
////		List<TourBatch> result = tourBatchServiceImpl.getFilteredBatches(minDate, maxDate);
////		assertThat(result).isNull();
//				
//	}
//	
//	@Test
//	@DisplayName("Test Filter for Min Date is null")
//	public void getFilterBatchesMinDateNull() throws ParseException {
//		String min_String= "26-09-2023";
//		String max_String = null;
//	    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");      
//	    Date minDate = formatter.parse(min_String); 
//	    Date maxDate = null;
//	    List<TourBatch> listOfTourBatch = new ArrayList<>();
//	    listOfTourBatch.add(tourBatch);
////	    when(batchRepository.findBatchesAfter(minDate)).thenReturn(listOfTourBatch);
////		List<TourBatch> result = tourBatchServiceImpl.getFilteredBatches(minDate, maxDate);
////		assertThat(result).isNotEmpty();
//				
//	}
//	
//	@Test
//	@DisplayName("Test Filter for max Date is null")
//	public void getFilterBatchesMaxDateNull() throws ParseException {
//		String min_String= "30-09-2023";
//		String max_String = null ;
//	    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");      
//	    Date minDate = formatter.parse(min_String); 
//	    Date maxDate = null;
//	    List<TourBatch> listOfTourBatch = new ArrayList<>();
//	    listOfTourBatch.add(tourBatch);
//	    when(batchRepository.findBatchesAfter(minDate)).thenReturn(listOfTourBatch);
////		List<TourBatch> result = tourBatchServiceImpl.getFilteredBatches(minDate, maxDate);
//		assertThat(result).isNotEmpty();
//				
//	}
//	@Test
//	@DisplayName("Test Filter")
//	public void getFilterBatchesForNull() throws ParseException {
////		List<TourBatch> result = tourBatchServiceImpl.getFilteredBatches(null, null);
//		assertThat(result).isNull();
//				
//	}
//	@Test
//	@DisplayName("get batch by user")
//	public void getBatchesByUserTest() throws Exception{
//		List<TourBatch> listOfTourbatch = new ArrayList<>();
//		listOfTourbatch.add(tourBatch);
//		Mockito.when(batchRepository.findAllByStatus(Status.ACTIVE)).thenReturn(listOfTourbatch);
//		List<TourBatch> response = tourBatchServiceImpl.getBatchesByUser();
//		assertThat(response).isNotEmpty();
//	}
//	@Test
//	@DisplayName("get batch by tour id")
//	public void getBatchesBytourId() throws Exception{
//		GuidedTour guidedTourInput = guidedTour;
//		List<TourBatch> listOfTourbatch = new ArrayList<>();
//		listOfTourbatch.add(tourBatch);
//		
//		Mockito.when(guidedTourRepository.findById(1l)).thenReturn(Optional.of(guidedTourInput));
//		Mockito.when(batchRepository.findAllByTourIdAndStatus(1l, Status.ACTIVE)).thenReturn(listOfTourbatch);
//		List<TourBatch> response = tourBatchServiceImpl.getBatchesByTourIdByUser(1l);
//		assertThat(response).isNotEmpty();
//	}
//	@Test
//	@DisplayName("get batch by tour id")
//	public void getBatchesBytourIdForNull() throws Exception{
//		List<TourBatch> listOfTourbatch = new ArrayList<>();
//		listOfTourbatch.add(tourBatch);
//		Mockito.when(guidedTourRepository.findById(1l)).thenReturn(Optional.empty());
//		List<TourBatch> response = tourBatchServiceImpl.getBatchesByTourIdByUser(1l);
//		assertThat(response).isNull();
//	}
//	
//	@Test
//	@DisplayName("get filter status")
//	public void getFilteredStatusTest() throws Exception{
//		List<TourBatch> listOfTourbatch = new ArrayList<>();
//		listOfTourbatch.add(tourBatch);
//		Mockito.when(batchRepository.findBatchByStatus(Status.ACTIVE)).thenReturn(listOfTourbatch);
//		List<TourBatch> response = tourBatchServiceImpl.getFilteredStatus(Status.ACTIVE);
//		assertThat(response).isNotEmpty();
//	}
//	
//	@Test
//	@DisplayName("get filter status")
//	public void getFilteredAvailableSeats() throws Exception{
//		int minSeat = -1;
//		int maxSeat = -1;
//		List<TourBatch> response = tourBatchServiceImpl.getFilteredAvailableSeats(minSeat , maxSeat);
//		assertThat(response).isNull();
//	}
//	@Test
//	@DisplayName("get filter status")
//	public void getFilteredAvailableSeatsForNullMinDate() throws Exception{
//		int minSeat = -1;
//		int maxSeat = 10;
//		List<TourBatch> listOfTourbatch = new ArrayList<>();
//		listOfTourbatch.add(tourBatch);
//		Mockito.when(batchRepository.findMaxAvailableSeats(maxSeat)).thenReturn(listOfTourbatch);
//		List<TourBatch> response = tourBatchServiceImpl.getFilteredAvailableSeats(minSeat , maxSeat);
//		assertThat(response).isNotEmpty();
//	}
//	@Test
//	@DisplayName("get filter status")
//	public void getFilteredAvailableSeatsForNullMaxDate() throws Exception{
//		int minSeat = 10;
//		int maxSeat = -1;
//		List<TourBatch> listOfTourbatch = new ArrayList<>();
//		listOfTourbatch.add(tourBatch);
//		Mockito.when(batchRepository.findMinAvailableSeats(minSeat)).thenReturn(listOfTourbatch);
//		List<TourBatch> response = tourBatchServiceImpl.getFilteredAvailableSeats(minSeat , maxSeat);
//		assertThat(response).isNotEmpty();
//	}
//	@Test
//	@DisplayName("get filter status")
//	public void getFilteredAvailableSeatsForNullminDateLessThanMax() throws Exception{
//		int minSeat = 1;
//		int maxSeat = 10;
//		List<TourBatch> listOfTourbatch = new ArrayList<>();
//		listOfTourbatch.add(tourBatch);
//		Mockito.when(batchRepository.findFilteredAvailableSeats(minSeat , maxSeat)).thenReturn(listOfTourbatch);
//		List<TourBatch> response = tourBatchServiceImpl.getFilteredAvailableSeats(minSeat , maxSeat);
//		assertThat(response).isNotEmpty();
//	}
//	
//	@Test
//	@DisplayName("get filter status")
//	public void getFilteredAvailableSeatsForNull() throws Exception{
//		int minSeat = -1;
//		int maxSeat = -1;
//		List<TourBatch> listOfTourbatch = new ArrayList<>();
//		listOfTourbatch.add(tourBatch);
//		List<TourBatch> response = tourBatchServiceImpl.getFilteredAvailableSeats(minSeat , maxSeat);
//		assertThat(response).isNull();
//	}
//	@Test
//	@DisplayName("get filter status")
//	public void getFilteredPriceForNull() throws Exception{
//		double budget = -1;
//		List<TourBatch> response = tourBatchServiceImpl.getFilteredPrice(budget);
//		assertThat(response).isNull();
//	}
//	@Test
//	@DisplayName("get filter status")
//	public void getFilteredPrice() throws Exception{
//		double budget = 1000;
//		List<TourBatch> listOfTourbatch = new ArrayList<>();
//		listOfTourbatch.add(tourBatch);
//		Mockito.when(batchRepository.findFilteredPrice(budget)).thenReturn(listOfTourbatch);
//		List<TourBatch> response = tourBatchServiceImpl.getFilteredPrice(budget);
//		assertThat(response).isNotEmpty();
//	}
//	
//}
