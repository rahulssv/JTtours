package com.jt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jt.model.tour.DifficultyLevel;
import com.jt.model.tour.GuidedTour;
import com.jt.model.tour.Mode;

@Repository
public interface TourRepository extends CrudRepository<GuidedTour, Long> {
	@Query("SELECT r from GuidedTour r WHERE r.days >= :minDays AND r.days <= :maxDays")
	List<GuidedTour> filterTourByDuration(@Param("minDays") int minDays, @Param("maxDays") int maxDays);

	@Query("SELECT r from GuidedTour r WHERE r.startAt = :source AND r.endAt = :destination")
	List<GuidedTour> filterToursByPlaceBetween(@Param("source") String source,
			@Param("destination") String destination);

	@Query("SELECT r from GuidedTour r WHERE r.startAt = :source")
	List<GuidedTour> filterToursByPlaceSource(@Param("source") String source);

	@Query("SELECT r from GuidedTour r WHERE r.endAt = :destination")
	List<GuidedTour> filterToursByPlaceDestination(@Param("destination") String destination);

	@Query("SELECT r from GuidedTour r WHERE r.mode = :mode")
	List<GuidedTour> filterToursByMode(@Param("mode") Mode mode);

	@Query("SELECT r from GuidedTour r WHERE r.difficultyLevel = :difficultyLevel")
	List<GuidedTour> filteredToursByDifficultyLevel(@Param("difficultyLevel") DifficultyLevel difficultyLevel);

	@Query(value = "SELECT t.* FROM guided_tour t " + "JOIN (SELECT tour_id, MAX(batch_completed) AS max_completed "
			+ "FROM (SELECT tb.tour_id, COUNT(*) AS batch_completed "
			+ "FROM tour_batch tb WHERE tb.status = 'COMPLETED' GROUP BY tb.tour_id) AS batches "
			+ "GROUP BY tour_id) AS max_batches " + "ON t.id = max_batches.tour_id "
			+ "ORDER BY max_batches.max_completed DESC " + "LIMIT 3", nativeQuery = true)
	List<GuidedTour> findMostPopularTours();
}
