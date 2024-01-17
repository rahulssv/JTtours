package com.jt.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
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
import com.fasterxml.jackson.databind.ObjectWriter;
import com.jt.model.batch.TourBatch;
import com.jt.model.tour.Accomodation;
import com.jt.model.tour.DayPlan;
import com.jt.model.tour.DifficultyLevel;
import com.jt.model.tour.GuidedTour;
import com.jt.model.tour.Itinerary;
import com.jt.model.tour.Mode;
import com.jt.model.tour.RoomType;
import com.jt.service.BatchService;
import com.jt.service.TourService;

@ExtendWith(MockitoExtension.class)
@DisplayName("TourController Testing")
public class TourControllerTest {
	
	private MockMvc mockMvc;
	
	ObjectMapper objectMapper = new ObjectMapper();
	ObjectWriter objectIdWriter = objectMapper.writer();
	
	@Mock
	private TourService tourService;
	
	@Mock
	private BatchService batchService;
	
	@InjectMocks 
	private TourController tourController;
	
	public static GuidedTour dummyDataForTour() {
		Accomodation accomodation = new Accomodation("harsh hotel" , "nagpur" , RoomType.TWIN_BED);
		DayPlan dayPlan = new DayPlan(1 , "nagpur" , 100.0 ,"harsh", accomodation);	
		List<DayPlan> listOfDayPlan = new ArrayList<>();
		listOfDayPlan.add(dayPlan);
		Itinerary itinerary = new Itinerary(listOfDayPlan);
		
		GuidedTour guidedTour = new GuidedTour();
		guidedTour.setId(1L);
		guidedTour.setName("mumbai-leh");
		guidedTour.setStartAt("mumbai");
		guidedTour.setEndAt("leh");
		guidedTour.setDays(5);
		guidedTour.setMode(Mode.WALK.toString());
		guidedTour.setDifficultyLevel(DifficultyLevel.EASY.toString());
		guidedTour.setItinerary(itinerary);
		return guidedTour;
	}
	public static TourBatch dummyDataForBatch() {
		TourBatch tourBatch = new TourBatch();
		LocalDate start_at = LocalDate.now();
		
		tourBatch.setId(1l);
		tourBatch.setStartDate(start_at);
		tourBatch.setCapacity(100);
		tourBatch.setTour(dummyDataForTour());

		return tourBatch;
	}
	@BeforeEach
	public void setUp() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(tourController).build();
	}
	
	@Test
	@DisplayName("Testing Get all tours list")
	public void getAllToursTest() throws Exception {
		List<GuidedTour> data = new ArrayList<>();
		data.add(TourControllerTest.dummyDataForTour());
		
		Mockito.when(tourService.getTours()).thenReturn(data);

		mockMvc.perform(MockMvcRequestBuilders
				.get("/tours")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$" , hasSize(1)))
				.andReturn().getResponse().getContentAsString();
	}
	@Test
	@DisplayName("Testing Get all tour for Not Found Status")
	public void getAllToursTestForNotFound() throws Exception {	
		Mockito.when(tourService.getTours()).thenReturn(null);

		mockMvc.perform(MockMvcRequestBuilders
				.get("/tours")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}
	@Test
	@DisplayName("Testing Get Tour by Id")
	public void getTourByIdTest() throws Exception {
		Mockito.when(tourService.getTourById(1L)).thenReturn(TourControllerTest.dummyDataForTour());
		
		mockMvc.perform(MockMvcRequestBuilders
				.get("/tours/1")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name", is("mumbai-leh")))
				.andExpect(jsonPath("$" , notNullValue()))
				.andReturn().getResponse().getContentAsString();
	}
	//Test will because not implemented status code for not found in controllers
	@Test
	@DisplayName("Testing Get Tour by Id when Invalid id passed")
	public void getTourByIdTesForInvalidId() throws Exception {
		Mockito.when(tourService.getTourById(2L)).thenReturn(null);
		 mockMvc.perform(MockMvcRequestBuilders
				.get("/tours/2")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andReturn().getResponse().getContentAsString();
		
	}
	
	@Test
	@DisplayName("Testing for create Tour")
	public void createTourTest() throws Exception {
		
		Mockito.when(tourService.addTour(Mockito.any(GuidedTour.class))).thenReturn(TourControllerTest.dummyDataForTour());
				
		String content = objectIdWriter.writeValueAsString(TourControllerTest.dummyDataForTour());
		
		MockHttpServletRequestBuilder mockRequestBuilder = MockMvcRequestBuilders.post("/tours")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(content);
		
		mockMvc.perform(mockRequestBuilder)
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$", notNullValue()))
				.andReturn().getResponse().getContentAsString();

	}
	@Test
	@DisplayName("Testing for create Tour For Invalid tour")
	public void createTourTestForNotFound() throws Exception {
		GuidedTour guidedTour = TourControllerTest.dummyDataForTour();
		Mockito.when(tourService.addTour(Mockito.any(GuidedTour.class))).thenReturn(null);
				
		String content = objectIdWriter.writeValueAsString(guidedTour);
		
		MockHttpServletRequestBuilder mockRequestBuilder = MockMvcRequestBuilders.post("/tours")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(content);
		
		mockMvc.perform(mockRequestBuilder)
				.andExpect(status().isNotAcceptable());

	}
	
	@Test
	@DisplayName("Testing for update tour")
	public void updateTourTest() throws Exception {
		
		GuidedTour guidedTour = TourControllerTest.dummyDataForTour();
		guidedTour.setName("nagpur-goa");
		
		Mockito.when(tourService.updateTour(Mockito.any(GuidedTour.class))).thenReturn(guidedTour);
		
		String content = objectIdWriter.writeValueAsString(guidedTour);
		
		MockHttpServletRequestBuilder mockRequestBuilder = MockMvcRequestBuilders.put("/tours")
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON)
					.content(content);
		
		String result = mockMvc.perform(mockRequestBuilder)
					.andExpect(status().isOk())
					.andExpect(jsonPath("$", notNullValue()))
					.andExpect(jsonPath("$.name", is("nagpur-goa")))
					.andReturn().getResponse().getContentAsString();
		
		System.out.println("-->"+result);
	}
	@Test
	@DisplayName("Testing for invalid tour get status not modified")
	public void updateTourTestForInvalidTour() throws Exception {
		
		GuidedTour guidedTour = TourControllerTest.dummyDataForTour();
		guidedTour.setName("nagpur-goa");
		
		Mockito.when(tourService.updateTour(Mockito.any(GuidedTour.class))).thenReturn(null);
		
		String content = objectIdWriter.writeValueAsString(guidedTour);
		
		MockHttpServletRequestBuilder mockRequestBuilder = MockMvcRequestBuilders.put("/tours")
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON)
					.content(content);
		
			mockMvc.perform(mockRequestBuilder)
					.andExpect(status().isNotModified());
	}
	
	@Test
	@DisplayName("Testing delete tour")
	public void deleteTourTest() throws Exception {
		Mockito.when(tourService.deleteTourById(1L)).thenReturn(true);
		
		mockMvc.perform(MockMvcRequestBuilders
			.delete("/tours/1")
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
		
		assertEquals(tourService.deleteTourById(1l), true);
	}
	
	@Test
	@DisplayName("Testing delete tour if fails")
	public void deleteTourNotAcceptableTest() throws Exception {
		  Mockito.when(tourService.deleteTourById(2L)).thenReturn(false); // assuming 2 is a case where deletion fails
		    mockMvc.perform(MockMvcRequestBuilders
		            .delete("/tours/2")
		            .contentType(MediaType.APPLICATION_JSON))
		            .andExpect(status().isNotAcceptable());
		    assertEquals(tourService.deleteTourById(2L), false);
	}
	
	
	@Disabled
	@Test
	@DisplayName("Testing get batch by tour id")
	public void getBatchByTourId() throws Exception {
		
		List<TourBatch> tourList = new ArrayList<>();
		tourList.add(BatchControllerTest.getDummyDataTourBatch());

		Mockito.when(tourService.getTourBatches(1l)).thenReturn(tourList);
		
		String response = mockMvc.perform(MockMvcRequestBuilders
				.get("/tours/1/batches")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();
			System.out.println("response-->"+response);
	}
	@Disabled
	@Test
	@DisplayName("Testing get batch by tour id when id not found")
	public void getBatchByTourIdForNotFound() throws Exception {
		Mockito.when(tourService.getTourBatches(1L)).thenReturn(null);
		
		mockMvc.perform(MockMvcRequestBuilders
				.get("/tours/1/batches")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
			
	}
	
	
	
	@Test
	@DisplayName("Testing Get Most Popular Tours")
	public void getMostPopularToursTest() throws Exception {
	    List<GuidedTour> popularTours = new ArrayList<>();
	    popularTours.add(TourControllerTest.dummyDataForTour());
	 
	    Mockito.when(tourService.getMostPopularTours()).thenReturn(popularTours);
	 
	    mockMvc.perform(MockMvcRequestBuilders
	            .get("/tours/popular")
	            .contentType(MediaType.APPLICATION_JSON))
	            .andExpect(status().isOk())
	            .andExpect(jsonPath("$", hasSize(1)))
	            .andReturn().getResponse().getContentAsString();
	}


	@Test
	@DisplayName("Testing Get Most Popular Tours for Not Found Status")
	public void getMostPopularToursNotFoundTest() throws Exception {	
		Mockito.when(tourService.getMostPopularTours()).thenReturn(null);

		mockMvc.perform(MockMvcRequestBuilders
				.get("/tours/popular")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}
	
	@Test
	@DisplayName("Testing Filter Tours by Duration")
	public void filterTourByDurationTest() throws Exception {
		List<GuidedTour> tours = new ArrayList<>();
		tours.add(TourControllerTest.dummyDataForTour());
	    Mockito.when(tourService.filterTourByDuration(3, 7)).thenReturn(tours);
	    mockMvc.perform(MockMvcRequestBuilders
	            .get("/tours/day")
	            .param("minDays", "3")
	            .param("maxDays", "7")
	            .contentType(MediaType.APPLICATION_JSON))
	            .andExpect(status().isOk())
	            .andExpect(jsonPath("$", hasSize(1)));
	    Mockito.when(tourService.filterTourByDuration(8, 10)).thenReturn(Collections.emptyList());
	 
	    mockMvc.perform(MockMvcRequestBuilders
	            .get("/tours/day")
	            .param("minDays", "8")
	            .param("maxDays", "10")
	            .contentType(MediaType.APPLICATION_JSON))
	            .andExpect(status().isNotFound());
	}
	
	@Test
	@DisplayName("Testing Filter Tours by Place")
	public void filteredToursByPlaceTest() throws Exception {
		List<GuidedTour> tours = new ArrayList<>();
		tours.add(TourControllerTest.dummyDataForTour());
	    Mockito.when(tourService.filteredToursByPlace("Mumbai", "Goa")).thenReturn(tours);
	 
	    mockMvc.perform(MockMvcRequestBuilders
	            .get("/tours/place")
	            .param("source", "Mumbai")
	            .param("destination", "Goa")
	            .contentType(MediaType.APPLICATION_JSON))
	            .andExpect(status().isOk())
	            .andExpect(jsonPath("$", hasSize(1)));
	    
	    Mockito.when(tourService.filteredToursByPlace("Delhi", "Jaipur")).thenReturn(Collections.emptyList());
	    mockMvc.perform(MockMvcRequestBuilders
	            .get("/tours/place")
	            .param("source", "Delhi")
	            .param("destination", "Jaipur")
	            .contentType(MediaType.APPLICATION_JSON))
	            .andExpect(status().isNotFound());
	}
	 
	@Test
	@DisplayName("Testing Filter Tours by Mode")
	public void filteredToursByModeTest() throws Exception {
		List<GuidedTour> tours = new ArrayList<>();
		tours.add(TourControllerTest.dummyDataForTour());
	    Mockito.when(tourService.filteredToursByMode(Mode.fromString("WALK"))).thenReturn(tours);
	 
	    mockMvc.perform(MockMvcRequestBuilders
	            .get("/tours/mode")
	            .param("mode", "WALK")
	            .contentType(MediaType.APPLICATION_JSON))
	            .andExpect(status().isOk())
	            .andExpect(jsonPath("$", hasSize(1)));

	    Mockito.when(tourService.filteredToursByMode(Mode.fromString("BICYCLE"))).thenReturn(Collections.emptyList());
	 
	    mockMvc.perform(MockMvcRequestBuilders
	            .get("/tours/mode")
	            .param("mode", "BICYCLE")
	            .contentType(MediaType.APPLICATION_JSON))
	            .andExpect(status().isNotFound());
	}
	 
	@Test
	@DisplayName("Testing Filter Tours by Difficulty Level")
	public void filteredToursByDifficultyLevelTest() throws Exception {
		List<GuidedTour> tours = new ArrayList<>();
		tours.add(TourControllerTest.dummyDataForTour());
	    Mockito.when(tourService.filteredToursByDifficultyLevel(DifficultyLevel.fromString("EASY"))).thenReturn(tours);
	 
	    mockMvc.perform(MockMvcRequestBuilders
	            .get("/tours/difficultyLevel")
	            .param("difficultyLevel", "EASY")
	            .contentType(MediaType.APPLICATION_JSON))
	            .andExpect(status().isOk())
	            .andExpect(jsonPath("$", hasSize(1)));

	    Mockito.when(tourService.filteredToursByDifficultyLevel(DifficultyLevel.fromString("HIGH"))).thenReturn(Collections.emptyList());
	 
	    mockMvc.perform(MockMvcRequestBuilders
	            .get("/tours/difficultyLevel")
	            .param("difficultyLevel", "HIGH")
	            .contentType(MediaType.APPLICATION_JSON))
	            .andExpect(status().isNotFound());
	}
}
