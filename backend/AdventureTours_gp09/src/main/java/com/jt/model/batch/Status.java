package com.jt.model.batch;

import java.util.ArrayList;
import java.util.List;

import com.jt.model.DisplayPair;

public enum Status {
	ACTIVE("Active"),
	FULL("Full"),
	IN_PROGRESS("In Progress"),
	CANCELLED("Cancelled"),
	COMPLETED("Completed");
	private final String status;

	public static List<DisplayPair<String, String>> getDisplayPairs() {
	 	List<DisplayPair<String, String>> DisplayPairList = new ArrayList<DisplayPair<String, String>>();
		for (Status status : Status.values()) {
			DisplayPairList.add(new DisplayPair<String, String>(status.name(), status.getStatus()));
		}
		return DisplayPairList;
	}
	 
	private Status(String status) {
		this.status = status;
	}

	private Status() {
		this.status="";
	}

	public String getStatus() {
		return status;
	}
	
	public static Status fromString(String status) {
	   
	    try {
	    	 return Status.valueOf(status.toUpperCase());
		}catch(IllegalArgumentException e) {
			throw new IllegalArgumentException("No enum constant for "+status);
		}
	}

	
	 
}
