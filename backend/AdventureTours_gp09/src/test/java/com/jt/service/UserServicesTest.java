package com.jt.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.security.crypto.password.PasswordEncoder;
import com.jt.controller.AuthController;
import com.jt.model.auth.Role;
import com.jt.model.auth.User;
import com.jt.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServicesTest {


	@Mock
	private UserRepository userRepo;

	@Mock
	private PasswordEncoder passwordEncoder;

	@InjectMocks
	private UserService userService;

	@InjectMocks
	private AuthController yourController;

	@Test
	@DisplayName("Test Add User as ADMIN")
	public void testAddUserAsAdmin() throws Exception {
		User user = new User();
		user.setRole(Role.ADMIN);
		user.setUserName("adminUser");
		user.setPassword("adminPassword");
        when(userRepo.findByRole(Role.valueOf("ADMIN"))).thenReturn(null);
		when(passwordEncoder.encode(user.getPassword())).thenReturn("encodedPassword");
		when(userRepo.save(user)).thenReturn(user);
		User result = userService.createTheUser(user);
		verify(userRepo, times(1)).save(user);
		assertNotNull(result);
		assertEquals(user, result);
	}
	
	@Test
	@DisplayName("Test Add User as ADMIN for null")
	public void testAddUserAsAdminForReturnNull() throws Exception {
		User user = new User();
		user.setRole(Role.ADMIN);
		user.setUserName("adminUser");
		user.setPassword("adminPassword");
        when(userRepo.findByRole(Role.valueOf("ADMIN"))).thenReturn(user);	
		User result = userService.createTheUser(user);
		assertThat(result).isNull();
	}
}
