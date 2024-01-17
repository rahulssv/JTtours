package com.jt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jt.model.auth.User;
import com.jt.service.UserService;

@RequestMapping("/users")
@RestController
public class UserController {
	@Autowired
	private UserService service;
	
	@PostMapping()
	public ResponseEntity<User> addUser(@RequestBody User user) throws Exception {
		User addUser=this.service.createTheUser(user);
		if(addUser!=null) {
			return new ResponseEntity<User>(addUser,HttpStatus.CREATED);
		}
		return new ResponseEntity<User>(addUser,HttpStatus.NOT_ACCEPTABLE);
	}
	@GetMapping()
	public ResponseEntity<List<User>> getUsers() {
		List<User> users=this.service.getAllTheUsers();
		if(users!=null) {
			return new ResponseEntity<List<User>>(users,HttpStatus.OK);
		}
		return new ResponseEntity<List<User>>(users,HttpStatus.NOT_FOUND);
		
	}
	
	
}
