package com.jt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jt.model.batch.Booking;
import com.jt.repository.BookingRepository;

@Service
public class BookingService {

	@Autowired
	private BookingRepository bookingRepository;

	public List<Booking> getAllBookings() {

		try {
			return (List<Booking>) this.bookingRepository.findAll();
		} catch (Exception e) {
			return null;
		}

	}
	
	public List<Booking> getAllBookingsUser(String username){
		try {
			return (List<Booking>) this.bookingRepository.findAllByUsername(username) ;
		}catch(Exception e) {
			return null ;
		}
	}
}
