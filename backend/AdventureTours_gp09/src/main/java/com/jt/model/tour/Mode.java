package com.jt.model.tour;

import java.util.ArrayList;
import java.util.List;

import com.jt.model.DisplayPair;

public enum Mode {
	WALK("Walk"), BICYCLE("Bicycle"), MOTORBIKE("Motorbike");
	private final String ModeType;

	public static List<DisplayPair<String, String>> getDisplayPairs() {
		List<DisplayPair<String, String>> DisplayPairList = new ArrayList<DisplayPair<String, String>>();
		for (Mode mode : Mode.values()) {
			DisplayPairList.add(new DisplayPair<String, String>(mode.name(), mode.ModeType));
		}
		return DisplayPairList;
	}

	private Mode(String modeType) {
		this.ModeType = modeType;
	}

	public String getModeType() {
		return this.ModeType;
	}

	public static Mode fromString(String modeType) {

		try {
			return Mode.valueOf(modeType.toUpperCase());
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("No enum constant for " + modeType);
		}
	}

}
