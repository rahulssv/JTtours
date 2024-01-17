package com.jt.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import com.jt.controller.BatchControllerTest;
import com.jt.controller.BookingControllerTest;
import com.jt.model.batch.Booking;
import com.jt.model.batch.TourBatch;
import com.jt.repository.BatchRepository;
import com.jt.repository.BookingRepository;
import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
@DisplayName("TourBatchService Testing")
public class BookingServiceTest {

	@Mock
	private BookingRepository bookingRepository;
	
	@Mock
	private BatchRepository batchRepository;
	
	@InjectMocks
	private BookingService bookingService;
	
	@InjectMocks
	private BatchService batchService;
	
	
	Booking book = BookingControllerTest.dummyBookingData();
	TourBatch batch = BatchControllerTest.getDummyDataTourBatch();
	@Test
	@DisplayName("Testing booking service addBook inside batchService")
	public void bookTravellerTest() {
		Mockito.when(bookingRepository.save(book)).thenReturn(book);
		Mockito.when(batchRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(batch));
		Mockito.when(batchRepository.save(batch)).thenReturn(batch);
		Mockito.when(bookingRepository.findAllByBatchId(book.getBatchId())).thenReturn(Arrays.asList(book));
		Booking bookResponse = batchService.bookTraveller(book);
		assertThat(bookResponse).isNotNull();
	}
	@Test
	@DisplayName("Testing booking service addBook inside batchService")
	public void bookTravellerTestForException() {
		Mockito.when(batchRepository.findById(Mockito.anyLong())).thenThrow(new EntityNotFoundException("batch not found for these booking"));
		Booking bookResponse = batchService.bookTraveller(book);
		assertThat(bookResponse).isNull();
	}
	@Test
	@DisplayName("Testing get all booking service")
	public void GetBookingsTest() {
		Mockito.when(bookingRepository.findAll()).thenReturn(Arrays.asList(book));
		
		List<Booking> bookResponse = bookingService.getAllBookings();
		
		assertThat(bookResponse).isNotEmpty();
	}
	@Test
	@DisplayName("Testing get all booking service for Exception")
	public void GetBookingsTestForInvalid() throws EntityNotFoundException{
		Mockito.when(bookingRepository.findAll()).thenThrow(new EntityNotFoundException("invalid case"));
		List<Booking> bookResponse = bookingService.getAllBookings();
		assertThat(bookResponse).isNull();
	}
	 @Test
	    @DisplayName("Testing get all bookings for a user")
	    public void getAllBookingsUserTest() {
	        // Arrange
	        String username = "testUser";
	        List<Booking> expectedBookings = Arrays.asList(book);
	 
	        Mockito.when(bookingRepository.findAllByUsername(username)).thenReturn(expectedBookings);
	        List<Booking> actualBookings = bookingService.getAllBookingsUser(username);
	        assertThat(actualBookings).isNotNull();
	        assertThat(actualBookings).isEqualTo(expectedBookings);
	    }
	 
	    @Test
	    @DisplayName("Testing get all bookings for a user when an exception occurs")
	    public void getAllBookingsUserTestForException() {
	        String username = "testUser";
	        Mockito.when(bookingRepository.findAllByUsername(username)).thenThrow(new RuntimeException("Test exception"));
	        List<Booking> actualBookings = bookingService.getAllBookingsUser(username);
	        assertThat(actualBookings).isNull();
	    }
}
