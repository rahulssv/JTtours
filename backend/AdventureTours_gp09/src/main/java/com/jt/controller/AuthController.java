package com.jt.controller;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jt.auth.AuthRequest;
import com.jt.auth.AuthResponse;
import com.jt.model.auth.User;
import com.jt.repository.UserRepository;
import com.jt.service.JwtService;
import com.jt.service.UserService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/auth")
public class AuthController {
	@Autowired
	private UserService service ;
	
	@Autowired
	private JwtService jwtService ;
	
	@Autowired
	private UserRepository userRepo ;
	
	@Autowired
	private AuthenticationManager authenticationManager ;
	
		
	@PostMapping("/login")
	public AuthResponse authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword()));
		if(authentication.isAuthenticated()) {
			Optional<User> fetchedUser = userRepo.findByUserName(authRequest.getUserName());
			if(fetchedUser.isPresent()) {
				String role = fetchedUser.get().getRole().toString() ;
				String token = jwtService.generateToken(authRequest.getUserName()) ;
				AuthResponse authResponse = new AuthResponse() ;
				authResponse.setToken(token) ;
				authResponse.setRole(role) ;
				authResponse.setUsername(authRequest.getUserName()) ;
				return authResponse ;
			}
			throw new UsernameNotFoundException("invalid user request !") ;
		}
		else {
			throw new UsernameNotFoundException("invalid user request !") ;
		}
		
	}
	
	@PostMapping("/register")
	public ResponseEntity<HttpStatus> createUser(@RequestBody User user) {
			User u = service.createTheUser(user) ;
			if(u!=null)
				return new ResponseEntity<HttpStatus>(HttpStatus.CREATED) ;
			else
				return new ResponseEntity<HttpStatus>(HttpStatus.NOT_ACCEPTABLE) ; 
	}

}
