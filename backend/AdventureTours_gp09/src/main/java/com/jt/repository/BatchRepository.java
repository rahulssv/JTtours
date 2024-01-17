package com.jt.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jt.model.auth.Role;
import com.jt.model.batch.Status;
import com.jt.model.batch.TourBatch;

import jakarta.transaction.Transactional;
@Repository
public interface BatchRepository extends CrudRepository<TourBatch, Long> {

	List<TourBatch> findAllByTourId(Long tourId);
	
	@Query(value="SELECT * FROM Tour_Batch WHERE "
			+ " CASE "
			+ " WHEN :role='CUSTOMER' THEN Tour_Batch.status='ACTIVE' AND Tour_Batch.tour_id=:tourId AND Tour_Batch.START_DATE >=:minDate AND Tour_Batch.START_DATE <=:maxDate "
			+ " WHEN :role='ADMIN' THEN Tour_Batch.tour_id=:tourId AND Tour_Batch.START_DATE >=:minDate AND Tour_Batch.START_DATE <=:maxDate"
			+ " END ", nativeQuery=true)
	List<TourBatch> findBatchesBetween(Long tourId, @Param("minDate") Date minDate, @Param("maxDate") Date maxDate, String role);

	@Query(value="SELECT * FROM Tour_Batch WHERE"
			+ " CASE "
			+ " WHEN :role='CUSTOMER' THEN Tour_Batch.status='ACTIVE' AND Tour_Batch.tour_id=:tourId AND Tour_Batch.START_DATE >=:minDate "
			+ " WHEN :role='ADMIN' THEN Tour_Batch.tour_id=:tourId AND Tour_Batch.START_DATE >=:minDate "
			+ " END", nativeQuery=true)
	List<TourBatch> findBatchesAfter(Long tourId, @Param("minDate") Date minDate, String role);
	
	@Query(value="SELECT * FROM Tour_Batch  WHERE "
			+ " CASE "
			+ " WHEN :role='CUSTOMER' THEN Tour_Batch.status='ACTIVE' AND Tour_Batch.tour_id=:tourId AND Tour_Batch.START_DATE <=:maxDate"
			+ " WHEN :role='ADMIN' THEN Tour_Batch.tour_id=:tourId AND Tour_Batch.START_DATE <=:maxDate"
			+ " END ", nativeQuery=true)
	List<TourBatch> findBatchesBefore(@Param("tourId") Long tourId, @Param("maxDate") Date maxDate,@Param("role") String role);
	
	List<TourBatch> findAllByTourIdAndStatus(Long tourId,Status status) ;
	
	List<TourBatch> findAllByStatus(Status status) ;
	
	@Query("SELECT r from TourBatch r WHERE r.tour.id=:tourId AND r.status = :status")
	List<TourBatch> findBatchesByStatusAdmin(Long tourId, Status status);
	
	@Transactional
	@Modifying
	@Query("UPDATE TourBatch t "
			+ "SET t.status = "
			+ "CASE "
			+ "WHEN t.availableSeats = t.capacity THEN 'CANCELLED' "
			+ "WHEN CURRENT_DATE() > t.endDate THEN 'COMPLETED' "
			+ "WHEN t.availableSeats < t.capacity THEN 'IN_PROGRESS' "
			+ "END "
			+ "WHERE CURRENT_DATE() > t.startDate")
	void updateStatus();
	
	@Query("SELECT r from TourBatch r WHERE r.status = :status")
	List<TourBatch> findBatchByStatus(@Param("status") Status status);
	
	@Query(value="SELECT * FROM Tour_Batch  WHERE "
			+ " CASE "
			+ " WHEN :role='CUSTOMER' THEN Tour_Batch.status='ACTIVE' AND Tour_Batch.tour_id=:tourId AND Tour_Batch.available_seats >=:minAvailableSeats"
			+ " WHEN :role='ADMIN' THEN Tour_Batch.tour_id=:tourId AND Tour_Batch.available_seats >=:minAvailableSeats"
			+ " END ", nativeQuery=true)
	List<TourBatch> findFilteredAvailableSeats(@Param("minAvailableSeats") int minAvailableSeats,@Param("tourId") Long tourId,@Param("role") String role);
	
	@Query(value="SELECT * FROM Tour_Batch  WHERE "
			+ " CASE "
			+ " WHEN :role='CUSTOMER' THEN Tour_Batch.status='ACTIVE' AND Tour_Batch.tour_id=:tourId AND Tour_Batch.per_participant_cost <=:budget"
			+ " WHEN :role='ADMIN' THEN Tour_Batch.tour_id=:tourId AND Tour_Batch.per_participant_cost <=:budget"
			+ " END ", nativeQuery=true)
	List<TourBatch> findFilteredPrice(@Param("budget") double budget,@Param("tourId") Long tourId,@Param("role") String role);
}
