package com.jt.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jt.model.DisplayPair;
import com.jt.service.MetadataService;

@RestController
@RequestMapping("/metadata")
@CrossOrigin(origins = "*")
public class MetadataController {
	@Autowired
	private MetadataService service;

	@GetMapping("/difficulties")
	public ResponseEntity<List<DisplayPair<String, String>>> getDifficultyLevels(){
		List<DisplayPair<String, String>>difficultyLevels =service.allDifficultyLevels();
		if(difficultyLevels!=null) {
			return new ResponseEntity<List<DisplayPair<String,String>>>(difficultyLevels,HttpStatus.OK);
		}
		return new ResponseEntity<List<DisplayPair<String,String>>>(HttpStatus.NOT_FOUND);
	}
	@GetMapping("/modes")
	public ResponseEntity<List<DisplayPair<String, String>>> getModes(){ 
		List<DisplayPair<String, String>>modes =service.allModes();
		if(modes!=null) {
			return new ResponseEntity<List<DisplayPair<String,String>>>(modes,HttpStatus.OK);
		}
		return new ResponseEntity<List<DisplayPair<String,String>>>(HttpStatus.NOT_FOUND);
	}
	@GetMapping("/roomtypes")
	public ResponseEntity<List<DisplayPair<String, String>>> getRoomTypes(){
		
		List<DisplayPair<String, String>>roomTypes =service.allRoomTypes();
		if(roomTypes!=null) {
			return new ResponseEntity<List<DisplayPair<String,String>>>(roomTypes,HttpStatus.OK);
		}
		return new ResponseEntity<List<DisplayPair<String,String>>>(HttpStatus.NOT_FOUND);
	}
	@GetMapping("/roles")
	public ResponseEntity<List<DisplayPair<String, String>>> getRoles(){
		List<DisplayPair<String, String>>roles =service.allRoles();
		if(roles!=null) {
			return new ResponseEntity<List<DisplayPair<String,String>>>(roles,HttpStatus.OK);
		}
		return new ResponseEntity<List<DisplayPair<String,String>>>(HttpStatus.NOT_FOUND);
	}
	@GetMapping("/batchstatuses")
	public ResponseEntity<List<DisplayPair<String, String>>> getStatus(){
		List<DisplayPair<String, String>>status =service.allStatus();
		if(status!=null) {
			return new ResponseEntity<List<DisplayPair<String,String>>>(status,HttpStatus.OK);
		}
		return new ResponseEntity<List<DisplayPair<String,String>>>(HttpStatus.NOT_FOUND);
	}

}
