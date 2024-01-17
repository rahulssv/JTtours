package com.jt.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.jt.model.DisplayPair;
import com.jt.model.batch.Status;
import com.jt.model.tour.DifficultyLevel;
import com.jt.model.tour.Mode;
import com.jt.model.tour.RoomType;
import com.jt.model.auth.Role;

@Service
public class MetadataService {

	public List<DisplayPair<String, String>> allDifficultyLevels() {
		try {
			return DifficultyLevel.getDisplayPairs();
		} catch (Exception e) {
			return null;
		}
		
	}

	public List<DisplayPair<String, String>> allModes() {
		try {
			return Mode.getDisplayPairs();
		} catch (Exception e) {
			return null;
		}
		
	}

	public List<DisplayPair<String, String>> allRoomTypes() {
		try {
			return RoomType.getDisplayPairs();
		} catch (Exception e) {
			return null;
		}
		
	}

	public List<DisplayPair<String, String>> allRoles() {
		try {
			return Role.getDisplayPairs();
		} catch (Exception e) {
			return null;
		}
		
	}

	public List<DisplayPair<String, String>> allStatus() {
		try {
			return Status.getDisplayPairs();
		} catch (Exception e) {
			return null;
		}
		
	}

}
