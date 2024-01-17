package com.jt.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.jt.model.batch.Booking;

public interface BookingRepository extends CrudRepository<Booking, Long>{
	
	List<Booking> findAllByUsername(String username);

	List<Booking> findAllByBatchId(Long id);

}
