package com.jt.model.tour;

import java.util.ArrayList;
import java.util.List;

import com.jt.model.DisplayPair;

public enum DifficultyLevel {
	HIGH("High"),
	MODERATE("Moderate"),
	EASY("Easy");
	private final String DifficultyLevelType;

	public static List<DisplayPair<String, String>> getDisplayPairs() {
		List<DisplayPair<String,String>> displayPairList = new ArrayList<DisplayPair<String, String>>() ;
		for (DifficultyLevel difficulty : DifficultyLevel.values()) {
			displayPairList.add(new DisplayPair<String, String>(difficulty.name(), difficulty.DifficultyLevelType));
		}
		return displayPairList;
	}

	private DifficultyLevel(String difficultyLevelType) {
		this.DifficultyLevelType = difficultyLevelType;
	}

	public String getDifficultyLevelType() {
		return this.DifficultyLevelType;
	}
	
	public static DifficultyLevel fromString(String difficultyLevel) {
	    
	    try {
	    	return DifficultyLevel.valueOf(difficultyLevel.toUpperCase());
		}catch(IllegalArgumentException e) {
			throw new IllegalArgumentException("No enum constant for "+difficultyLevel);
		}
	}
	
}