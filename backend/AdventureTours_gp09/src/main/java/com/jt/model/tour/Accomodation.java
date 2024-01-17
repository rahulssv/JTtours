package com.jt.model.tour;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Accomodation {

	private String hotelName;
	private String address;
	
	@Enumerated(EnumType.STRING)
	private RoomType roomType;
	
	public void setRoomType(String roomType) {
		this.roomType = RoomType.fromString(roomType);
	}
	
	

}
