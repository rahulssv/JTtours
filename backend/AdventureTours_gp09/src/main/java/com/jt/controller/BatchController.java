package com.jt.controller;


import java.util.Collections;
import java.util.Date;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jt.config.UserInfoUserDetails;
import com.jt.model.auth.Role;
import com.jt.model.batch.Booking;
import com.jt.model.batch.Status;
import com.jt.model.batch.TourBatch;
import com.jt.service.BatchService;

@RequestMapping("/batches")
@RestController
@CrossOrigin(origins = "*")
public class BatchController {

	@Autowired
	private BatchService batchService;
	
	@PostMapping("/book")
	@PreAuthorize("hasAuthority('CUSTOMER')")
	public ResponseEntity<Booking> bookTraveller(@RequestBody Booking booking) {
		Booking newbooking = batchService.bookTraveller(booking);
		if (newbooking != null) {
			return new ResponseEntity<Booking>(newbooking, HttpStatus.CREATED);
		}
		return new ResponseEntity<Booking>(newbooking, HttpStatus.NOT_ACCEPTABLE);
	}

	
	@PostMapping("/tours/{tourId}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<TourBatch> createBatch(@PathVariable Long tourId, @RequestBody TourBatch batch) {
		if(this.batchService.addBatch(tourId, batch)!=null) {
			return new ResponseEntity<TourBatch>(batch,HttpStatus.CREATED);
		}
		return new ResponseEntity<TourBatch>(HttpStatus.NOT_FOUND);
	}

	
	@GetMapping()
	public ResponseEntity< List<TourBatch>> getAllBatches(@AuthenticationPrincipal UserInfoUserDetails userDetails){
		// checking whether someone is logged in or not based on that showing the batches
		System.out.println("inside get batches");
		if(userDetails!=null && userDetails.getAuthorities().stream()
				.anyMatch(a->a.getAuthority().equals("ADMIN"))) {
			List<TourBatch> batches = batchService.getBatches();
			if(batches != null)
				return new ResponseEntity<List<TourBatch>>(batches,HttpStatus.OK);
			return new ResponseEntity<List<TourBatch>>(HttpStatus.NOT_FOUND);
		}else {
			List<TourBatch> batches = batchService.getBatchesByUser();
			if(batches != null)
				return new ResponseEntity<List<TourBatch>>(batches,HttpStatus.OK);
			return new ResponseEntity<List<TourBatch>>(HttpStatus.NOT_FOUND);
		}
		
	}
	
	
	@PutMapping()
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<TourBatch> updateBatch(@RequestBody TourBatch tourBatch) {
		TourBatch updatedBatch = batchService.updateBatch(tourBatch);
		if(updatedBatch!=null) {
			return new ResponseEntity<TourBatch>(updatedBatch,HttpStatus.OK);
		}
		return new ResponseEntity<TourBatch>(tourBatch,HttpStatus.NOT_MODIFIED);
	}
	
	
	@GetMapping("/{batchId}")
	public ResponseEntity<TourBatch> getBatchById(@PathVariable Long batchId) {
		TourBatch batch =  batchService.getBatchById(batchId);
		if(batch!=null)
			 return new ResponseEntity<TourBatch>(batch,HttpStatus.OK);
		return new ResponseEntity<TourBatch>(HttpStatus.NOT_FOUND);
	}
	
	@DeleteMapping("/{batchId}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<HttpStatus> deleteBatchById(@PathVariable Long batchId) {
		if( batchService.deleteBatchById(batchId) == true) {
			return new ResponseEntity<HttpStatus>(HttpStatus.OK);
		}
		return new ResponseEntity<HttpStatus>(HttpStatus.NOT_ACCEPTABLE); 
	}
	
	@GetMapping("/{tourId}/startDate")
	public ResponseEntity< List<TourBatch>> getFilteredBatches(@AuthenticationPrincipal UserInfoUserDetails userDetails,
			@PathVariable Long tourId, @RequestParam(value = "minDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date minDate,
			@RequestParam(value = "maxDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date maxDate) {
		
		List<TourBatch> batches = null;
		if(userDetails!=null && userDetails.getAuthorities().stream().anyMatch(a->a.getAuthority().equals("ADMIN"))) {
			batches = batchService.getFilteredBatches(tourId,minDate, maxDate, Role.ADMIN);
		}
		else {
			batches = batchService.getFilteredBatches(tourId,minDate, maxDate, Role.CUSTOMER);
		}
		if(batches==null)
			return new ResponseEntity<List<TourBatch>>(Collections.emptyList(),HttpStatus.NOT_ACCEPTABLE);
		if(batches.isEmpty())
			return new ResponseEntity<List<TourBatch>>(Collections.emptyList(),HttpStatus.NOT_FOUND);
		
		return new ResponseEntity<List<TourBatch>>(batches,HttpStatus.OK);
	}
	
	@GetMapping("/{tourId}/status")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<List<TourBatch>> getFilteredStatus(@PathVariable Long tourId,
			@RequestParam(value = "status") Status status) {
		
		List<TourBatch> tourBatch=batchService.getFilteredStatus(tourId, status);
			if(tourBatch==null)
				return new ResponseEntity<List<TourBatch>>(Collections.emptyList(),HttpStatus.NOT_ACCEPTABLE);
			if(tourBatch.isEmpty())
				return new ResponseEntity<List<TourBatch>>(Collections.emptyList(),HttpStatus.NOT_FOUND);
		return new ResponseEntity<List<TourBatch>>(tourBatch,HttpStatus.OK);
			
	}
	
	@GetMapping("/{tourId}/availableSeats")
	public ResponseEntity<List<TourBatch>> getFilteredAvailableSeats(@AuthenticationPrincipal UserInfoUserDetails userDetails,
			@PathVariable Long tourId, @RequestParam(value = "minAvailableSeats", required = false, defaultValue="-1") int minAvailableSeats
			) {
		List<TourBatch> batches=null;
		if(userDetails!=null && userDetails.getAuthorities().stream().anyMatch(a->a.getAuthority().equals("ADMIN"))) {
			batches = batchService.getFilteredAvailableSeats(tourId,minAvailableSeats, Role.ADMIN);
		}
		else {
			batches = batchService.getFilteredAvailableSeats(tourId,minAvailableSeats, Role.CUSTOMER);
		}
		
		if(batches==null)
			return new ResponseEntity<List<TourBatch>>(Collections.emptyList(),HttpStatus.NOT_ACCEPTABLE);
		if(batches.isEmpty())
			return new ResponseEntity<List<TourBatch>>(Collections.emptyList(),HttpStatus.NOT_FOUND);
		
		return new ResponseEntity<List<TourBatch>>(batches,HttpStatus.OK);
	
	}
	
	@GetMapping("/{tourId}/perParticipantCost")
	public ResponseEntity<List<TourBatch>> getFilteredPrice(@AuthenticationPrincipal UserInfoUserDetails userDetails,
			@PathVariable Long tourId,
			@RequestParam(value = "budget") double budget) {
		
		List<TourBatch> batches=null;
		if(userDetails!=null && userDetails.getAuthorities().stream().anyMatch(a->a.getAuthority().equals("ADMIN"))) {
			batches = batchService.getFilteredPrice(tourId, budget, Role.ADMIN);
		}
		else {
			batches = batchService.getFilteredPrice(tourId, budget, Role.CUSTOMER);
		}
		if(batches==null)
			return new ResponseEntity<List<TourBatch>>(Collections.emptyList(),HttpStatus.NOT_ACCEPTABLE);
		if(batches.isEmpty())
			return new ResponseEntity<List<TourBatch>>(Collections.emptyList(),HttpStatus.NOT_FOUND);
	
		return new ResponseEntity<List<TourBatch>>(batches,HttpStatus.OK);
	}
	
}
