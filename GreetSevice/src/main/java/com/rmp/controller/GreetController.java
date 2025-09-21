package com.rmp.controller;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetController {
	@GetMapping("/greet")
	public ResponseEntity<Map<String, Object>> showWelcomeMessage(){
		Map<String, Object>map=new LinkedHashMap<>();
		map.put("name","Rudra");
		map.put("Date",LocalDate.now());
		map.put("msg","Good Morning");
		return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
	}
	

}
