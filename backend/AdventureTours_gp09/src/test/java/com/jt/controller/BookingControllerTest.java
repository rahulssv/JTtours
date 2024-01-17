package com.jt.controller;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.Arrays;
import java.util.Date;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.jt.model.batch.Booking;
import com.jt.model.user.MobileNumber;
import com.jt.model.user.Name;
import com.jt.model.user.Traveller;
import com.jt.service.BatchService;
import com.jt.service.BookingService;

@ExtendWith(MockitoExtension.class)
@DisplayName("TourController Testing")
public class BookingControllerTest {

	private MockMvc mockMvc;

	ObjectMapper objectMapper = new ObjectMapper();
	ObjectWriter objectIdWriter = objectMapper.writer();

	@Mock
	private BookingService bookingService;
	
	@Mock
	private BatchService batchService;
	
	
	
	@InjectMocks
	private BookingController bookingController;
	
	
	@BeforeEach
	public void setUp() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(bookingController).build();
	}
	

	public static Booking dummyBookingData() {
		Booking booking = new Booking();
		Date date = new Date();
//		Traveller traveller = new Traveller(1, new Name("harsh", "fulzele"), new MobileNumber("1234567890"));
		Traveller traveller = new Traveller();
		traveller.setBookingSequence(1);
		traveller.setMobile(new MobileNumber("9022018440"));
		traveller.setName(new Name("harsh" ,"fulzele"));
	
		booking.setId(1l);
		booking.setBatchId(1l);
		booking.setDate(date);
		booking.setAmount(12000.0);
		booking.setUsername("harsh@gmail.com");
		booking.setTravellers(Arrays.asList(traveller));
		return booking;
	}

	
	@Disabled
	@Test
	@DisplayName("Testing get Bookings")
	public void getBookings() throws Exception {
		
		Mockito.when(bookingService.getAllBookings()).thenReturn(Arrays.asList(dummyBookingData()));
		
		mockMvc.perform(MockMvcRequestBuilders
		.get("/bookings")
		.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());

	}
	@Disabled
	@Test
	@DisplayName("Testing get Bookings not not found status code")
	public void getBookingsNotFound() throws Exception {
		
		Mockito.when(bookingService.getAllBookings()).thenReturn(null);
		
		mockMvc.perform(MockMvcRequestBuilders
		.get("/bookings")
		.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isNotFound());

	}
}


