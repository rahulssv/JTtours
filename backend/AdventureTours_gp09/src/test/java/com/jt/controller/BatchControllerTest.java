package com.jt.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
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
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.jt.model.batch.Booking;
import com.jt.model.batch.Status;
import com.jt.model.batch.TourBatch;
import com.jt.model.tour.GuidedTour;
import com.jt.model.user.MobileNumber;
import com.jt.model.user.Name;
import com.jt.model.user.Traveller;
import com.jt.service.BatchService;

@ExtendWith(MockitoExtension.class)
@DisplayName("BatchController Testing")
public class BatchControllerTest {
	
	private MockMvc mockMvc;

	ObjectMapper objectMapper = JsonMapper.builder()
            .addModule(new JavaTimeModule())
            .build();;
	ObjectWriter objectIdWriter = objectMapper.writer();
	
	@Mock
	private BatchService tourBatchService;

	@InjectMocks
	private BatchController tourBatchController;

	public static TourBatch getDummyDataTourBatch() {
		LocalDate date=LocalDate.now();
		TourBatch tb = new TourBatch();
		GuidedTour gt = new GuidedTour();
		Traveller tv=new Traveller();
		MobileNumber mn=new MobileNumber();
		mn.setNumber("9234567899");
		Name name=new Name();
		name.setFirst("harsh");
		name.setLast("last");
		
		tv.setMobile(mn);
		tv.setName(name);
		tv.setBookingSequence(0);
		
		gt.setId(1L);
		
		tb.setId(33L);
		tb.setStartDate(date);
		tb.setCapacity(55);
		tb.setPerParticipantCost(300d);
		tb.setEndDate(5);
		tb.setStatus(Status.ACTIVE.toString());
		tb.setGuide(tv);
		tb.setTour(TourControllerTest.dummyDataForTour());
		tb.setAvailableSeats(20);
		return tb;
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

	@BeforeEach
	public void setUp() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(tourBatchController).build();
	}
	@Test
	@DisplayName("Testing Add book test")
	public void AddBookingTest() throws UnsupportedEncodingException, Exception {
		
		Booking book = this.dummyBookingData();
		System.out.println(book);
		Mockito.when(tourBatchService.bookTraveller(Mockito.any(Booking.class))).thenReturn(book);
		
		String content = objectIdWriter.writeValueAsString(this.dummyBookingData());
		
		MockHttpServletRequestBuilder mockRequestBuilder = MockMvcRequestBuilders.post("/batches/book")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(content);
		
		mockMvc.perform(mockRequestBuilder)
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$", notNullValue()))
		.andReturn().getResponse().getContentAsString();

	}
	
	@Test
	@DisplayName("Testing Add book test for not Acceptable test")
	public void AddBookingTestForInvalidTest() throws UnsupportedEncodingException, Exception {
		
		Mockito.when(tourBatchService.bookTraveller(Mockito.any(Booking.class))).thenReturn(null);
		
		String content = objectIdWriter.writeValueAsString(this.dummyBookingData());
		
		MockHttpServletRequestBuilder mockRequestBuilder = MockMvcRequestBuilders.post("/batches/book")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(content);
		
		mockMvc.perform(mockRequestBuilder)
		.andExpect(status().isNotAcceptable());

	}
	@Disabled
	@Test
	@DisplayName("Test for getting all tour batches")
	public void getAllTourBatchesTest() throws UnsupportedEncodingException, Exception {
		
		List<TourBatch> tbList = new ArrayList<>();
		tbList.add(BatchControllerTest.getDummyDataTourBatch());

		Mockito.when(tourBatchService.getBatches()).thenReturn(tbList);

		mockMvc.perform(MockMvcRequestBuilders.get("/batches").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", notNullValue()))
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].id", is(33)))
				.andReturn().getResponse().getContentAsString();
	}
	@Disabled
	@Test
	@DisplayName("Test for getting all tour batches")
	public void getAllTourBatchesTestForEmptyList() throws UnsupportedEncodingException, Exception {
		
		List<TourBatch> tbList = new ArrayList<>();
		tbList.add(BatchControllerTest.getDummyDataTourBatch());

		Mockito.when(tourBatchService.getBatches()).thenReturn(null);

		mockMvc.perform(MockMvcRequestBuilders.get("/batches")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$").doesNotExist());
	}
	@Disabled
	@Test
	@DisplayName("Test for getting batch by id")
	public void getTourBatchByIdTest() throws Exception {
		Mockito.when(tourBatchService.getBatchById(33L)).thenReturn(BatchControllerTest.getDummyDataTourBatch());

		mockMvc.perform(MockMvcRequestBuilders.get("/batches/33").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isFound())
				.andExpect(jsonPath("$", notNullValue()))
				.andExpect(jsonPath("$.id", is(33)))
				.andReturn().getResponse().getContentAsString();
	}
	
	@Test
	@DisplayName("Test for getting batch by id for Invalid Id")
	public void getTourBatchByIdTestForInvalidId() throws Exception {
		Mockito.when(tourBatchService.getBatchById(33L)).thenReturn(null);

		mockMvc.perform(MockMvcRequestBuilders.get("/batches/33").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$").doesNotExist());
	}

	@Test
	@DisplayName("Test for updating tour batch")
	public void updateTourBatchTest() throws Exception {
		TourBatch tb = BatchControllerTest.getDummyDataTourBatch();
		tb.setCapacity(99);
		Mockito.when(tourBatchService.updateBatch(Mockito.any(TourBatch.class))).thenReturn(tb);
		String content = objectIdWriter.writeValueAsString(tb);
		MockHttpServletRequestBuilder mockRequestBuilder = MockMvcRequestBuilders.put("/batches")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(content);
		mockMvc.perform(mockRequestBuilder)
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.capacity", is(99)))
				.andReturn().getResponse().getContentAsString();
	}
	@Test
	@DisplayName("Test for updating tour batch")
	public void updateTourBatchTestForInvalidStatus() throws Exception {
		TourBatch tb = BatchControllerTest.getDummyDataTourBatch();
		tb.setCapacity(99);
		Mockito.when(tourBatchService.updateBatch(Mockito.any(TourBatch.class))).thenReturn(null);
		String content = objectIdWriter.writeValueAsString(tb);
		MockHttpServletRequestBuilder mockRequestBuilder = MockMvcRequestBuilders.put("/batches")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(content);
		mockMvc.perform(mockRequestBuilder)
				.andExpect(status().isNotModified());
	}

	@Test
	@DisplayName("Test for deleting tour batch")
	public void deleteTourBatchTest() throws Exception {
		Mockito.when(tourBatchService.deleteBatchById(1L)).thenReturn(true);
		mockMvc.perform(MockMvcRequestBuilders.delete("/batches/1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

	}

	@Test
	@DisplayName("Test for deleting tour batch for Invalid Id")
	public void deleteTourBatchTestForInvalidId() throws Exception {
		Mockito.when(tourBatchService.deleteBatchById(1L)).thenReturn(false);
		mockMvc.perform(MockMvcRequestBuilders.delete("/batches/1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotAcceptable());

	}
	@Disabled
	@Test
	@DisplayName("Test for getting tour batch by start date")
	public void getTourBatchByStartDateTest() throws Exception {
		List <TourBatch> tbList = new ArrayList<>();
//		Mockito.when(tourBatchService.getFilteredBatches(null, null)).thenReturn(tbList);
		
		String content = objectIdWriter.writeValueAsString(tbList);
		
		MockHttpServletRequestBuilder mockRequestBuilder = MockMvcRequestBuilders.get("/batches/startDate")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content);
		
		 mockMvc.perform(mockRequestBuilder)
		 .andExpect(status().isOk())
		 .andExpect(jsonPath("$", hasSize(0)))
		 .andReturn().getResponse().getContentAsString();
	}
	@Test
	@DisplayName("Testing create batch")
	public void createBatchTest() throws Exception {
		
		TourBatch tbd = BatchControllerTest.getDummyDataTourBatch();
		Mockito.when(tourBatchService.addBatch(Mockito.anyLong() ,Mockito.any(TourBatch.class))).thenReturn(tbd);
		
		String content = objectIdWriter.writeValueAsString(tbd);

		MockHttpServletRequestBuilder mockRequestBuilder = MockMvcRequestBuilders.post("/batches/tours/1")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(content);

		mockMvc.perform(mockRequestBuilder)
		.andExpect(status().isCreated())
		.andReturn().getResponse().getContentAsString();
	}
	@Test
	@DisplayName("Testing create batch for Empty Batch")
	public void createBatchTestForEmptyBatch() throws Exception {
		
		TourBatch tbd = BatchControllerTest.getDummyDataTourBatch();
		Mockito.when(tourBatchService.addBatch(Mockito.anyLong() ,Mockito.any(TourBatch.class))).thenReturn(null);
		
		String content = objectIdWriter.writeValueAsString(tbd);

		MockHttpServletRequestBuilder mockRequestBuilder = MockMvcRequestBuilders.post("/batches/tours/1")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(content);

		mockMvc.perform(mockRequestBuilder)
		.andExpect(status().isNotFound())
		.andReturn().getResponse().getContentAsString();
	}
	
}
