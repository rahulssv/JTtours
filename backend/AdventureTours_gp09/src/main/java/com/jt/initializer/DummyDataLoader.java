package com.jt.initializer;

import java.time.LocalDate;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.jt.model.auth.Role;
import com.jt.model.auth.User;
import com.jt.model.batch.TourBatch;
import com.jt.model.tour.Accomodation;
import com.jt.model.tour.DayPlan;
import com.jt.model.tour.GuidedTour;
import com.jt.model.tour.Itinerary;
import com.jt.model.tour.RoomType;
import com.jt.model.user.MobileNumber;
import com.jt.model.user.Name;
import com.jt.model.user.Traveller;
import com.jt.repository.BatchRepository;
import com.jt.repository.TourRepository;
import com.jt.repository.UserRepository;

import jakarta.annotation.PostConstruct;

@Component
public class DummyDataLoader {

	@Autowired
	private TourRepository tourRepository;

	@Autowired
	BatchRepository batchRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder ;

	@PostConstruct
	public void loadDummyTourData() {
		
		
		//Add Adming 
		User admin = new User();
		admin.setPassword(passwordEncoder.encode("Admin@123"));
		admin.setRole(Role.ADMIN);
		admin.setUserName("admin@gmail.com");
		userRepository.save(admin);
		
		
		//Add User 
		User user1 = new User();
		user1.setPassword(passwordEncoder.encode("Krishna@123"));
		user1.setRole(Role.CUSTOMER);
		user1.setUserName("krishna@gmail.com");
		userRepository.save(user1);
		
		
		// Tour 1
		GuidedTour guidedTour1 = new GuidedTour();
		guidedTour1.setName("Mumbai-Nasik");
		guidedTour1.setMode("Bicycle");
		guidedTour1.setDifficultyLevel("Easy");
		guidedTour1.setStartAt("Mumbai");
		guidedTour1.setEndAt("Nasik");
		Itinerary itinerary1 = new Itinerary();
		itinerary1.setDayPlans(Arrays.asList(
				new DayPlan(1, "Mumbai", 0,
						"reporting between 5 pm to 7 pm. Introduction, tour briefing and ice breaker",
						new Accomodation("Hotel Mayur", "SB road, Pune", RoomType.DOUBLE_BED)),
				new DayPlan(2, "Nasik", 150, "Ride on Mumbai Agra road",
						new Accomodation("Hotel Dwaraka", "Dwaraka, Nashik", RoomType.TWIN_BED)),
				new DayPlan(3, "Nasik", 0,
						"Breakfast, Certificate distribution and tour ends. Participants return home",
						new Accomodation("Hotel Dwaraka", "Dwaraka, Nashik", RoomType.TWIN_BED))));
		guidedTour1.setItinerary(itinerary1);
		tourRepository.save(guidedTour1);

		TourBatch batch1Tour1 = new TourBatch();
		batch1Tour1.setTour(guidedTour1);
		batch1Tour1.setStartDate(LocalDate.now());
		batch1Tour1.setEndDate(guidedTour1.getDays());
		batch1Tour1.setCapacity(5);
		batch1Tour1.setPerParticipantCost(3000.0);
		batch1Tour1.setStatus("Completed");
		batch1Tour1.setGuide(new Traveller(0, new Name("Rahul", "Sharma"), new MobileNumber("+91866388475")));
		batchRepository.save(batch1Tour1);

		TourBatch batch2Tour1 = new TourBatch();
		batch2Tour1.setTour(guidedTour1);
		batch2Tour1.setStartDate(LocalDate.of(2023, 10, 5));
		batch2Tour1.setEndDate(guidedTour1.getDays());
		batch2Tour1.setCapacity(10);
		batch2Tour1.setPerParticipantCost(1000.0);
		batch2Tour1.setStatus("Completed");
		batch2Tour1.setGuide(new Traveller(0, new Name("Ram", "Thakur"), new MobileNumber("+917856765643")));
		batchRepository.save(batch2Tour1);

		TourBatch batch3Tour1 = new TourBatch();
		batch3Tour1.setTour(guidedTour1);
		batch3Tour1.setStartDate(LocalDate.of(2023, 11, 6));
		batch3Tour1.setEndDate(guidedTour1.getDays());
		batch3Tour1.setCapacity(5);
		batch3Tour1.setPerParticipantCost(3000.0);
		batch3Tour1.setStatus("Active");
		batch3Tour1.setGuide(new Traveller(0, new Name("Raj", "Nikam"), new MobileNumber("+91866388475")));
		batchRepository.save(batch3Tour1);

		TourBatch batch4Tour1 = new TourBatch();
		batch4Tour1.setTour(guidedTour1);
		batch4Tour1.setStartDate(LocalDate.of(2023,10,23));
		batch4Tour1.setEndDate(guidedTour1.getDays());
		batch4Tour1.setCapacity(10);
		batch4Tour1.setPerParticipantCost(3000.0);
		batch4Tour1.setStatus("Completed");
		batch4Tour1.setGuide(new Traveller(0, new Name("Rahul", "Sharma"), new MobileNumber("+91866388475")));
		batchRepository.save(batch4Tour1);

		// Tour 2
		GuidedTour guidedTour2 = new GuidedTour();
		guidedTour2.setName("Jaipur-Udaipur");
		guidedTour2.setMode("Motorbike");
		guidedTour2.setDifficultyLevel("Moderate");
		guidedTour2.setStartAt("Jaipur");
		guidedTour2.setEndAt("Udaipur");
		Itinerary itinerary2 = new Itinerary();
		itinerary2.setDayPlans(Arrays.asList(
				new DayPlan(1, "Jaipur", 0, "Check-in and tour briefing",
						new Accomodation("Hotel Royal", "City Center, Jaipur", RoomType.DOUBLE_BED)),
				new DayPlan(2, "Jaipur", 120, "Explore Amber Fort and local markets",
						new Accomodation("Heritage Palace", "Old City, Jaipur", RoomType.DOUBLE_BED)),
				new DayPlan(3, "Udaipur", 300, "Drive to Udaipur and visit City Palace",
						new Accomodation("Lake View Resort", "Pichola, Udaipur", RoomType.TWIN_BED))));
		guidedTour2.setItinerary(itinerary2);
		tourRepository.save(guidedTour2);
		
		
		TourBatch batch1Tour2 = new TourBatch();
		batch1Tour2.setTour(guidedTour2);
		batch1Tour2.setStartDate(LocalDate.of(2023,10,23));
		batch1Tour2.setEndDate(guidedTour2.getDays());
		batch1Tour2.setCapacity(10);
		batch1Tour2.setPerParticipantCost(3000.0);
		batch1Tour2.setStatus("Completed");
		batch1Tour2.setGuide(new Traveller(0, new Name("Rahul", "Sharma"), new MobileNumber("+91866388475")));
		batchRepository.save(batch1Tour2);
		
		TourBatch batch2Tour2 = new TourBatch();
		batch2Tour2.setTour(guidedTour2);
		batch2Tour2.setStartDate(LocalDate.of(2023, 11, 6));
		batch2Tour2.setEndDate(guidedTour2.getDays());
		batch2Tour2.setCapacity(5);
		batch2Tour2.setPerParticipantCost(3000.0);
		batch2Tour2.setStatus("Active");
		batch2Tour2.setGuide(new Traveller(0, new Name("Raj", "Nikam"), new MobileNumber("+91866388475")));
		batchRepository.save(batch2Tour2);

		TourBatch batch3Tour2 = new TourBatch();
		batch3Tour2.setTour(guidedTour2);
		batch3Tour2.setStartDate(LocalDate.of(2023,10,23));
		batch3Tour2.setEndDate(guidedTour2.getDays());
		batch3Tour2.setCapacity(10);
		batch3Tour2.setPerParticipantCost(3000.0);
		batch3Tour2.setStatus("Active");
		batch3Tour2.setGuide(new Traveller(0, new Name("Rahul", "Sharma"), new MobileNumber("+91866388475")));
		batchRepository.save(batch3Tour2);

		// Tour 3
		GuidedTour guidedTour3 = new GuidedTour();
		guidedTour3.setName("Himalayan Trek");
		guidedTour3.setMode("Bicycle");
		guidedTour3.setDifficultyLevel("High");
		guidedTour3.setStartAt("Manali");
		guidedTour3.setEndAt("Leh");
		Itinerary itinerary3 = new Itinerary();
		itinerary3
				.setDayPlans(
						Arrays.asList(
								new DayPlan(1, "Manali", 0, "Check-in and trek briefing",
										new Accomodation("Mountain Lodge", "Himalayan Base, Manali",
												RoomType.TWIN_BED)),
								new DayPlan(2, "Himalayas", 180, "Hike through scenic trails",
										new Accomodation("Alpine Camp", "High Altitude Campsite", RoomType.DOUBLE_BED)),
								new DayPlan(3, "Leh", 240, "Reach Leh and explore local markets",
										new Accomodation("Valley View Hotel", "Downtown Leh", RoomType.DOUBLE_BED))));
		guidedTour3.setItinerary(itinerary3);
		tourRepository.save(guidedTour3);
		
		
		TourBatch batch1Tour3 = new TourBatch();
		batch1Tour3.setTour(guidedTour3);
		batch1Tour3.setStartDate(LocalDate.of(2023,10,23));
		batch1Tour3.setEndDate(guidedTour3.getDays());
		batch1Tour3.setCapacity(10);
		batch1Tour3.setPerParticipantCost(3000.0);
		batch1Tour3.setStatus("Completed");
		batch1Tour3.setGuide(new Traveller(0, new Name("Makarand", "Sharma"), new MobileNumber("+91866388475")));
		batchRepository.save(batch1Tour3);
		
		TourBatch batch2Tour3 = new TourBatch();
		batch2Tour3.setTour(guidedTour3);
		batch2Tour3.setStartDate(LocalDate.of(2023,10,23));
		batch2Tour3.setEndDate(guidedTour3.getDays());
		batch2Tour3.setCapacity(10);
		batch2Tour3.setPerParticipantCost(1000.0);
		batch2Tour3.setStatus("Active");
		batch2Tour3.setGuide(new Traveller(0, new Name("Rahul", "Vishwakarama"), new MobileNumber("+91866388475")));
		batchRepository.save(batch2Tour3);
		
		TourBatch batch3Tour3 = new TourBatch();
		batch3Tour3.setTour(guidedTour3);
		batch3Tour3.setStartDate(LocalDate.of(2023,11,11));
		batch3Tour3.setEndDate(guidedTour3.getDays());
		batch3Tour3.setCapacity(5);
		batch3Tour3.setPerParticipantCost(3000.0);
		batch3Tour3.setStatus("Completed");
		batch3Tour3.setGuide(new Traveller(0, new Name("Rahul", "Sharma"), new MobileNumber("+91866388475")));
		batchRepository.save(batch3Tour3);
		
		TourBatch batch4Tour3 = new TourBatch();
		batch3Tour2.setTour(guidedTour3);
		batch3Tour2.setStartDate(LocalDate.of(2023,11,23));
		batch3Tour2.setEndDate(guidedTour3.getDays());
		batch3Tour2.setCapacity(3);
		batch3Tour2.setPerParticipantCost(5000.0);
		batch3Tour2.setStatus("Completed");
		batch3Tour2.setGuide(new Traveller(0, new Name("Rajni", "Kamath"), new MobileNumber("+91866388475")));
		batchRepository.save(batch3Tour2);
		
		TourBatch batch5Tour3 = new TourBatch();
		batch5Tour3.setTour(guidedTour3);
		batch5Tour3.setStartDate(LocalDate.of(2023,9,23));
		batch5Tour3.setEndDate(guidedTour3.getDays());
		batch5Tour3.setCapacity(10);
		batch5Tour3.setPerParticipantCost(2000.0);
		batch5Tour3.setStatus("Completed");
		batch5Tour3.setGuide(new Traveller(0, new Name("Rahul", "Sharma"), new MobileNumber("+91866388475")));
		batchRepository.save(batch5Tour3);
		

		// Tour 4
		GuidedTour guidedTour4 = new GuidedTour();
		guidedTour4.setName("Amazon Rainforest Exploration");
		guidedTour4.setMode("Motorbike");
		guidedTour4.setDifficultyLevel("Moderate");
		guidedTour4.setStartAt("Manaus");
		guidedTour4.setEndAt("Iquitos");
		Itinerary itinerary4 = new Itinerary();
		itinerary4.setDayPlans(Arrays.asList(
				new DayPlan(1, "Manaus", 0, "Board boat and start the journey",
						new Accomodation("River Cruise Ship", "Amazon River", RoomType.TWIN_BED)),
				new DayPlan(2, "Amazon River", 240, "Explore the diverse wildlife",
						new Accomodation("Jungle Lodge", "Deep in the Rainforest", RoomType.DOUBLE_BED)),
				new DayPlan(3, "Iquitos", 120, "Reach Iquitos and visit local communities",
						new Accomodation("Riverside Resort", "Iquitos", RoomType.DOUBLE_BED))));
		guidedTour4.setItinerary(itinerary4);
		tourRepository.save(guidedTour4);
		
		TourBatch batch1Tour4 = new TourBatch();
		batch1Tour4.setTour(guidedTour4);
		batch1Tour4.setStartDate(LocalDate.of(2023,9,03));
		batch1Tour4.setEndDate(guidedTour4.getDays());
		batch1Tour4.setCapacity(5);
		batch1Tour4.setPerParticipantCost(2500.0);
		batch1Tour4.setStatus("Active");
		batch1Tour4.setGuide(new Traveller(0, new Name("Aniket", "Kadam"), new MobileNumber("+91866388475")));
		batchRepository.save(batch1Tour4);

		// Tour 5
		GuidedTour guidedTour5 = new GuidedTour();
		guidedTour5.setName("Cultural Delights of Kyoto");
		guidedTour5.setMode("Walk");
		guidedTour5.setDifficultyLevel("Easy");
		guidedTour5.setStartAt("Kyoto");
		guidedTour5.setEndAt("Osaka");
		Itinerary itinerary5 = new Itinerary();
		itinerary5
				.setDayPlans(
						Arrays.asList(
								new DayPlan(1, "Kyoto", 0, "Check-in and explore local temples",
										new Accomodation("Traditional Inn", "Gion District, Kyoto",
												RoomType.DOUBLE_BED)),
								new DayPlan(2, "Kyoto", 180, "Visit historic shrines and traditional tea ceremony",
										new Accomodation("Ryokan Kumo", "Higashiyama, Kyoto", RoomType.TWIN_BED)),
								new DayPlan(3, "Osaka", 60, "Travel to Osaka and enjoy street food",
										new Accomodation("City View Hotel", "Dotonbori, Osaka", RoomType.DOUBLE_BED))));
		guidedTour5.setItinerary(itinerary5);
		tourRepository.save(guidedTour5);
		
		TourBatch batch1Tour5 = new TourBatch();
		batch1Tour5.setTour(guidedTour5);
		batch1Tour5.setStartDate(LocalDate.of(2023,6,2));
		batch1Tour5.setEndDate(guidedTour5.getDays());
		batch1Tour5.setCapacity(20);
		batch1Tour5.setPerParticipantCost(1000.0);
		batch1Tour5.setStatus("Completed");
		batch1Tour5.setGuide(new Traveller(0, new Name("Rahul", "Aaand"), new MobileNumber("+91866388475")));
		batchRepository.save(batch1Tour5);

		TourBatch batch2Tour5 = new TourBatch();
		batch2Tour5.setTour(guidedTour5);
		batch2Tour5.setStartDate(LocalDate.of(2023,8,21));
		batch2Tour5.setEndDate(guidedTour5.getDays());
		batch2Tour5.setCapacity(10);
		batch2Tour5.setPerParticipantCost(5000.0);
		batch2Tour5.setStatus("Active");
		batch2Tour5.setGuide(new Traveller(0, new Name("Harshit", "Tiware"), new MobileNumber("+91866388475")));
		batchRepository.save(batch2Tour5);
		
		// Tour 6
//		GuidedTour guidedTour6 = new GuidedTour();
//		guidedTour6.setName("Safari Adventure in Serengeti");
//		guidedTour6.setMode("Motorbike");
//		guidedTour6.setDifficultyLevel("High");
//		guidedTour6.setStartAt("Arusha");
//		guidedTour6.setEndAt("Ngorongoro");
//		Itinerary itinerary6 = new Itinerary();
//		itinerary6.setDayPlans(Arrays.asList(
//				new DayPlan(1, "Arusha", 0, "Arrival and safari orientation",
//						new Accomodation("Safari Lodge", "Arusha", RoomType.DOUBLE_BED)),
//				new DayPlan(2, "Serengeti", 360, "Full-day safari adventure",
//						new Accomodation("Tented Camp", "Serengeti National Park", RoomType.TWIN_BED)),
//				new DayPlan(3, "Ngorongoro", 180, "Visit Ngorongoro Crater and wildlife observation",
//						new Accomodation("Crater View Resort", "Ngorongoro Conservation Area", RoomType.DOUBLE_BED))));
//		guidedTour6.setItinerary(itinerary6);
//		tourRepository.save(guidedTour6);
	}
}