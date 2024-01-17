package com.jt.service;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.jt.model.batch.Status;
import com.jt.model.auth.Role;
import com.jt.model.batch.Booking;
import com.jt.model.batch.TourBatch;
import com.jt.model.tour.GuidedTour;
import com.jt.model.user.Traveller;
import com.jt.repository.TourRepository;
import com.jt.repository.BatchRepository;
import com.jt.repository.BookingRepository;

@Service
public class BatchService {

	@Autowired
	private BatchRepository tourBatchRepository;
	private TourBatch batch;
	@Autowired
	private TourRepository guidedTourRepository;
	private GuidedTour tour;

	@Autowired
	private BookingRepository bookingRepository;
	private List<Booking> bookings;

	public Booking bookTraveller(Booking booking) {

		try {
			
			Optional<TourBatch> fetchedBatch = this.tourBatchRepository.findById(booking.getBatchId());
			System.out.println(fetchedBatch);
			if(fetchedBatch.isPresent()) {
				this.batch = fetchedBatch.get();
				int bookingSeats=0;
				bookingSeats=this.batch.getAvailableSeats() - booking.getTravellers().size() ;
				if (bookingSeats>= 0) {
					this.bookingRepository.save(booking);
					bookings = this.bookingRepository.findAllByBatchId(booking.getBatchId());
					int bookedSeats = 0;
					int sequence = this.batch.getCapacity() - this.batch.getAvailableSeats();
					for (Booking booked : bookings) {
						bookedSeats += booked.getTravellers().size();
					}
					List<Traveller> travellers = booking.getTravellers();
					for (Traveller traveller : travellers) {
						traveller.setBookingSequence(++sequence);
					}
					this.batch.setAvailableSeats(bookedSeats);
					this.tourBatchRepository.save(this.batch);
					return booking;
				}
			return null;
			} else {
				return null;
			}
		} catch (Exception e) {
			return null;
		}

	}

	public TourBatch addBatch(Long tour_id, TourBatch batch) {
		try {
			Optional<GuidedTour> fetchedTour = this.guidedTourRepository.findById(tour_id);
			if(fetchedTour.isPresent()) {				
				this.tour = fetchedTour.get();
				batch.setTour(this.tour);
				batch.setEndDate(this.tour.getDays());
				batch.setStatus("Active");
				batch.getGuide().setBookingSequence(0);
				int bookedSeats = 0;
				batch.setAvailableSeats(bookedSeats);
				this.tourBatchRepository.save(batch);
				return batch;
			}
			return null;
		} catch (Exception e) {
//			System.out.println(e.getMessage());
			return null;
		}
		
	}

	public List<TourBatch> getBatchesByUser() {
		return tourBatchRepository.findAllByStatus(Status.ACTIVE);
	}

	public List<TourBatch> getBatchesByTourId(Long tourId) {
		GuidedTour tour = guidedTourRepository.findById(tourId).orElse(null);
		if (tour != null)
			return tourBatchRepository.findAllByTourId(tourId);
		return null;

	}

	public List<TourBatch> getBatchesByTourIdByUser(Long tourId) {
		GuidedTour tour = guidedTourRepository.findById(tourId).orElse(null);
		if (tour != null) {
			return tourBatchRepository.findAllByTourIdAndStatus(tourId, Status.ACTIVE);
		}
		return null;

	}

	public List<TourBatch> getBatches() {
		try {
			return (List<TourBatch>) this.tourBatchRepository.findAll();
		} catch (Exception e) {
			return null;
		}
	}

	public TourBatch getBatchById(Long batch_id) {
		try {
			Optional<TourBatch> fetchedBatch = this.tourBatchRepository.findById(batch_id);
			if(fetchedBatch.isPresent()) {
				return fetchedBatch.get();
			}
			return null;
		} catch (Exception e) {
			return null;
		}

	}

	public boolean deleteBatchById(Long batch_id) {
		try {
			Optional<TourBatch> fetchedBatch = this.tourBatchRepository.findById(batch_id);
			if (fetchedBatch.isPresent()) {
				this.batch = fetchedBatch.get();
				this.batch.setStatus("Cancelled");
				this.tourBatchRepository.save(this.batch);
				return true;
			}
			else {
				return false;
			}		
		} catch (Exception e) {
			return false;
		}

	}

	public TourBatch updateBatch(TourBatch batch) {
		if (this.getBatchById(batch.getId()) != null) {
			return tourBatchRepository.save(batch);
		}
		return null;
	}


	
	public List<TourBatch> getFilteredBatches(Long tourId, Date minDate, Date maxDate, Role role) {

		if (maxDate == null && minDate == null)
			return null;

		if (minDate != null) {
			if (maxDate != null) {
				if (minDate.after(maxDate))
					return null;
				return tourBatchRepository.findBatchesBetween(tourId, minDate, maxDate, role.toString());
			}
			return tourBatchRepository.findBatchesAfter(tourId, minDate, role.toString());
		}
		return tourBatchRepository.findBatchesBefore(tourId, maxDate, role.toString());
	}

	@Scheduled(cron = "0 2 0 * * *")
	public void updateStatus() {
		System.out.println("Updating Status of TourBatch on date-change");
		tourBatchRepository.updateStatus();
	}
	
	public List<TourBatch> getFilteredStatus(Long tourId, Status status) {
		return tourBatchRepository.findBatchesByStatusAdmin(tourId, status);
	}

	public List<TourBatch> getFilteredAvailableSeats(Long tourId, int minAvailableSeats, Role role) {
		if (minAvailableSeats == -1) {
			return null;
		}
			return tourBatchRepository.findFilteredAvailableSeats(minAvailableSeats, tourId, role.toString());
	}
	
	public List<TourBatch> getFilteredPrice(Long tourId, double budget, Role role) {
		if (budget < 0 || budget > 10000000 )
			return null;
		return tourBatchRepository.findFilteredPrice(budget, tourId, role.toString());
	}

}
