package com.jt.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.Optional;
import org.springframework.security.core.Authentication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.jt.auth.AuthRequest;
import com.jt.auth.AuthResponse;
import com.jt.model.auth.Role;
import com.jt.model.auth.User;
import com.jt.repository.UserRepository;
import com.jt.service.JwtService;
import com.jt.service.UserService;

@ExtendWith(MockitoExtension.class)
@DisplayName("AuthController Testing")
public class AuthControllerTest {

private MockMvc mockMvc;
	
	ObjectMapper objectMapper = new ObjectMapper();
	ObjectWriter objectIdWriter = objectMapper.writer();

	@Mock
	private UserService userService;
	
	@Mock
	private JwtService jwtService;
	
	@Mock
	private UserRepository userRepository;
	
	@Mock
	private AuthenticationManager authenticationManager;
	
	@InjectMocks
	private AuthController authController;
	
	@BeforeEach
	public void setUp() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
	}
	
	public static User dummuyUser() {
		User user = new User();
		user.setUserName("harsh@gmail.com");
		user.setPassword("harsh");
		user.setRole(Role.CUSTOMER);
		return user;
	}
	public static AuthRequest dummyAuthRequest() {
		AuthRequest authRequest = new AuthRequest();
		authRequest.setUserName("harsh@gmail.com");
		authRequest.setPassword("harsh");
		return authRequest;
	}
	@Test
	@DisplayName("Testing register")
	public void createUserTest() throws Exception {
		User user = this.dummuyUser();
//		Mockito.when(userService.addUser(user)).thenReturn(this.dummuyUser());
		when(userService.createTheUser(user)).thenReturn(user);
		
		String content  = objectIdWriter.writeValueAsString(user);
		MockHttpServletRequestBuilder mockRequestBuilder = MockMvcRequestBuilders.post("/auth/register")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(content);
		
		mockMvc.perform(mockRequestBuilder)
				.andExpect(status().isCreated());

	}
	@Test
	@DisplayName("Testing register of empty user")
	public void createUserTestForIvalid() throws Exception {
		User user = null;
		String contentInput  = objectIdWriter.writeValueAsString(user);
		MockHttpServletRequestBuilder mockRequestBuilder = MockMvcRequestBuilders.post("/auth/register")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(contentInput);
		
		mockMvc.perform(mockRequestBuilder)
				.andExpect(status().isBadRequest());

	}
	
	@Test
	@DisplayName("Testing login test")
	public void authenticateAndGetTokenTest() throws Exception {
		AuthRequest authRequest = this.dummyAuthRequest();
		User user = this.dummuyUser();
		
		AuthResponse authResponse = new AuthResponse();
		authResponse.setToken("jwtToken");
		
		Authentication authentication = Mockito.mock(Authentication.class);
		
		when(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword())))
		.thenReturn(authentication);
		
		when(jwtService.generateToken(authRequest.getUserName())).thenReturn("MockedJwtString");
		
		when(authentication.isAuthenticated()).thenReturn(true);
		String content = objectIdWriter.writeValueAsString(authRequest);
		
		when(userRepository.findByUserName(user.getUserName())).thenReturn(Optional.of(user));
		MockHttpServletRequestBuilder mockRequestBuilder = MockMvcRequestBuilders.post("/auth/login")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(content);
		
		mockMvc.perform(mockRequestBuilder)
				.andExpect(jsonPath("$.token", is("MockedJwtString")));

	}
	
	@Test
    @DisplayName("Testing login test for invalid credentials")
    public void authenticateAndGetTokenTestForInvalid() throws Exception {
        AuthRequest authRequest = this.dummyAuthRequest();

        Authentication authentication = Mockito.mock(Authentication.class);

        when(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword())))
                .thenReturn(authentication);

        when(authentication.isAuthenticated()).thenReturn(false);

        String content = objectIdWriter.writeValueAsString(authRequest);
        try {
        	 mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
                     .contentType(MediaType.APPLICATION_JSON)
                     .content(content))
                     .andExpect(status().isOk())
                     .andExpect(jsonPath("$.error").value("UsernameNotFoundException"))
                     .andExpect(jsonPath("$.message").value("invalid user request"));
        	
        }catch(Exception e) {
        	e.printStackTrace();
        }
       
    }
}
