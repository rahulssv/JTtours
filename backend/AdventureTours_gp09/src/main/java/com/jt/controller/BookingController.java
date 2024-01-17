package com.jt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jt.config.UserInfoUserDetails;
import com.jt.model.batch.Booking;
import com.jt.service.BookingService;

@RequestMapping("/bookings")
@RestController
@CrossOrigin(origins = "*")
public class BookingController {
	@Autowired
	private BookingService service;
	
	@GetMapping()
	public ResponseEntity<List<Booking>> getBookings(@AuthenticationPrincipal UserInfoUserDetails userDetails) {
		List<Booking> bookings = null ;
		Boolean isAdmin=userDetails.getAuthorities().stream().anyMatch(a->a.getAuthority().equals("ADMIN"));
		if(userDetails!=null && isAdmin)
				bookings = service.getAllBookings();			
		else
			bookings = service.getAllBookingsUser(userDetails.getUsername());
		
		if(bookings!=null) {
			return new ResponseEntity<List<Booking>>(bookings,HttpStatus.OK);
		}
		return new ResponseEntity<List<Booking>>(bookings, HttpStatus.NOT_FOUND);
		
	}
}
