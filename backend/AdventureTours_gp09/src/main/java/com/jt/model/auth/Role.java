package com.jt.model.auth;

import java.util.ArrayList;
import java.util.List;

import com.jt.model.DisplayPair;

public enum Role {

	ADMIN("ADMIN"), CUSTOMER("CUSTOMER");
	private final String role;
	private static List<DisplayPair<String, String>> DisplayPairList = new ArrayList<DisplayPair<String, String>>();

	public static List<DisplayPair<String, String>> getDisplayPairs() {
		for (Role s : Role.values()) {
			DisplayPairList.add(new DisplayPair<String, String>(s.name(), s.role));
		}
		return DisplayPairList;
	}

	private Role(String role) {
		this.role = role;
	}

	private Role() {
		this.role = "";
	}

	public String getRole() {
		return role;
	}

	public static Role fromString(String role) {

		try {
			return Role.valueOf(role.toUpperCase());
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("No enum constant for " + role);
		}
	}

	@Override
	public String toString() {
		return this.getRole();
	}

}
