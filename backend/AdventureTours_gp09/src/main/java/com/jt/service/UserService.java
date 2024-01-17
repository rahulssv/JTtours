package com.jt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jt.model.auth.Role;
import com.jt.model.auth.User;
import com.jt.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepo ;
	
	@Autowired
	private PasswordEncoder passwordEncoder ;

	public User createTheUser(User user) {
		String role = user.getRole().toString() ;

			if(role.equals("ADMIN")) {
				User u = userRepo.findByRole(Role.valueOf("ADMIN")) ;
				if(u==null) {
					user.setPassword(passwordEncoder.encode(user.getPassword()));
					return userRepo.save(user) ;
				}
				else {
					return null ;
				}
			}
			else {
				User u = userRepo.findByUserName(user.getUserName()).orElse(null) ;
				System.out.println("user"+u) ;
				if(u==null) {
					user.setPassword(passwordEncoder.encode(user.getPassword()));
					return userRepo.save(user) ;
				}
				return null ;
			}
		
		
	}
	
	public  List<User> getAllTheUsers() {
		try {
			return (List<User>) this.userRepo.findAll();
		}catch(Exception e) {
			return null;
		}
	}
	
}
