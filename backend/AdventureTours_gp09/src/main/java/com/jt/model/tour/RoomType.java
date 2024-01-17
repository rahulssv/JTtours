package com.jt.model.tour;

import java.util.ArrayList;
import java.util.List;

import com.jt.model.DisplayPair;

public enum RoomType {
    TWIN_BED("Twin Bed"), DOUBLE_BED("Double Bed");

	private final String roomType;

	RoomType(String roomType) {
		this.roomType = roomType;
	}

	public String getRoomType() {
		return roomType;
	}

	public static RoomType fromString(String roomType) {
		try {
			return RoomType.valueOf(roomType.toUpperCase());
		}
		catch(Exception e) {
			for (RoomType room : RoomType.values()) {
				System.out.println(room.getRoomType());
				if (room.getRoomType().toUpperCase().equals(roomType.toUpperCase()))
					return room;
			}
		}
		throw new IllegalArgumentException("No enum constant for " + roomType);
	}

	public static List<DisplayPair<String, String>> getDisplayPairs() {
		List<DisplayPair<String, String>> DisplayPairList = new ArrayList<DisplayPair<String, String>>();
		for (RoomType room : RoomType.values()) {
			DisplayPairList.add(new DisplayPair<String, String>(room.name(), room.roomType));
		}
		return DisplayPairList;
	}
}