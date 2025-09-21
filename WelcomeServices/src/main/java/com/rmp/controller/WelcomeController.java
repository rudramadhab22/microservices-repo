package com.rmp.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {
	@GetMapping("/welcome")
	public ResponseEntity<Map<String, Object>> showWelcomeMessage(){
		Map<String, Object>map=new LinkedHashMap<>();
		map.put("msg","Welcome Rudra");
		return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
	}
	

}
