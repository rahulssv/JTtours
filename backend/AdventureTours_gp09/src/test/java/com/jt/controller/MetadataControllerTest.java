package com.jt.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
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
import com.jt.model.DisplayPair;
import com.jt.service.MetadataService;

@ExtendWith(MockitoExtension.class)
@DisplayName("TourController Testing")
public class MetadataControllerTest {

	
	private MockMvc mockMvc;
	

	@Mock
	private MetadataService metadataService;
	
	@InjectMocks
	private MetadataController metadataController;
	
	ObjectMapper objectMapper = new ObjectMapper();
	ObjectWriter objectIdWriter = objectMapper.writer();

	public static DisplayPair<String, String> getDummyDisplaiPair() {
		DisplayPair<String, String> data = new DisplayPair<>();
		data.setOptionValue("ADMIN");
		data.setOptionDisplayString("Admin");
		return data;
	}
	@BeforeEach
	public void setUp() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(metadataController).build();
	}
	
	@Test
	@DisplayName("Testing metadata difficulty")
	public void getDifficultyLevel() throws UnsupportedEncodingException, Exception {
		List<DisplayPair<String, String>>difficultyLevels = new ArrayList<>();
		difficultyLevels.add(this.getDummyDisplaiPair());
		
		Mockito.when(metadataService.allDifficultyLevels()).thenReturn(difficultyLevels);
		
		mockMvc.perform(MockMvcRequestBuilders
				.get("/metadata/difficulties")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
	@Test
	@DisplayName("Testing metadata difficulty")
	public void getDifficultyLevelForNotFound() throws UnsupportedEncodingException, Exception {
		List<DisplayPair<String, String>>difficultyLevels = new ArrayList<>();
		difficultyLevels.add(this.getDummyDisplaiPair());
		
		Mockito.when(metadataService.allDifficultyLevels()).thenReturn(null);
		
		mockMvc.perform(MockMvcRequestBuilders
				.get("/metadata/difficulties")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}
	@Test
	@DisplayName("Testing metadata modes")
	public void getDifficultyLevelMode() throws UnsupportedEncodingException, Exception {
		List<DisplayPair<String, String>>modesList = new ArrayList<>();
		modesList.add(this.getDummyDisplaiPair());
		
		Mockito.when(metadataService.allModes()).thenReturn(modesList);
		
		mockMvc.perform(MockMvcRequestBuilders
				.get("/metadata/modes")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
	
	@Test
	@DisplayName("Testing metadata modes")
	public void getDifficultyLevelModeForInvalid() throws UnsupportedEncodingException, Exception {
		List<DisplayPair<String, String>>modesList = new ArrayList<>();
		modesList.add(this.getDummyDisplaiPair());
		
		Mockito.when(metadataService.allModes()).thenReturn(null);
		
		mockMvc.perform(MockMvcRequestBuilders
				.get("/metadata/modes")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}
	
	@Test
	@DisplayName("Testing metadata modes")
	public void getRoomTypes() throws UnsupportedEncodingException, Exception {
		List<DisplayPair<String, String>>roomType = new ArrayList<>();
		roomType.add(this.getDummyDisplaiPair());
		
		Mockito.when(metadataService.allRoomTypes()).thenReturn(roomType);
		
		mockMvc.perform(MockMvcRequestBuilders
				.get("/metadata/roomtypes")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
	@Test
	@DisplayName("Testing metadata modes")
	public void getRoomTypesForNotFound() throws UnsupportedEncodingException, Exception {
		List<DisplayPair<String, String>>roomType = new ArrayList<>();
		roomType.add(this.getDummyDisplaiPair());
		
		Mockito.when(metadataService.allRoomTypes()).thenReturn(null);
		
		mockMvc.perform(MockMvcRequestBuilders
				.get("/metadata/roomtypes")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}
	@Test
	@DisplayName("Testing metadata modes")
	public void getRoles() throws UnsupportedEncodingException, Exception {
		List<DisplayPair<String, String>>rolesList = new ArrayList<>();
		rolesList.add(this.getDummyDisplaiPair());
		
		Mockito.when(metadataService.allRoles()).thenReturn(rolesList);
		
		mockMvc.perform(MockMvcRequestBuilders
				.get("/metadata/roles")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
	@Test
	@DisplayName("Testing metadata modes")
	public void getRolesForInvalid() throws UnsupportedEncodingException, Exception {
		List<DisplayPair<String, String>>rolesList = new ArrayList<>();
		rolesList.add(this.getDummyDisplaiPair());
		
		Mockito.when(metadataService.allRoles()).thenReturn(null);
		
		mockMvc.perform(MockMvcRequestBuilders
				.get("/metadata/roles")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}
	
	@Test
	@DisplayName("Testing metadata modes")
	public void getBatchStatus() throws UnsupportedEncodingException, Exception {
		List<DisplayPair<String, String>>batchStatus = new ArrayList<>();
		batchStatus.add(this.getDummyDisplaiPair());
		
		Mockito.when(metadataService.allStatus()).thenReturn(batchStatus);
		
		mockMvc.perform(MockMvcRequestBuilders
				.get("/metadata/batchstatuses")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
	
	@Test
	@DisplayName("Testing metadata modes")
	public void getBatchStatusForInvalid() throws UnsupportedEncodingException, Exception {
		List<DisplayPair<String, String>>batchStatus = new ArrayList<>();
		batchStatus.add(this.getDummyDisplaiPair());
		
		Mockito.when(metadataService.allStatus()).thenReturn(null);
		
		mockMvc.perform(MockMvcRequestBuilders
				.get("/metadata/batchstatuses")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}
	
}
