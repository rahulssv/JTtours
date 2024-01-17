package com.jt.model.user;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Traveller {

//	@GeneratedValue(strategy = GenerationType.AUTO)
	private int bookingSequence;
	@Embedded
	Name name;

	@Embedded
	MobileNumber mobile;


}
