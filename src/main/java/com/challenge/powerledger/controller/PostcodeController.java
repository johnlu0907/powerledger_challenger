package com.challenge.powerledger.controller;

import java.util.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.challenge.powerledger.dto.*;
import com.challenge.powerledger.service.PostcodeService;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Validated
@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
@Slf4j
public class PostcodeController {
	@Autowired
	private PostcodeService postcodeService;
	
	@PostMapping("/insert")
	public ResponseEntity<String> insert(
			@Valid
			@RequestBody 
			@NotEmpty(message = "postcode list cannot be empty.") 
			List<@Valid PostcodeDto> request) throws Exception {
		log.info("request: " + request);
		postcodeService.saveAll(request);
		
		return new ResponseEntity<>(
				"Inserted postcode successfully", HttpStatus.CREATED);
	}
	
	@GetMapping("/list")
	public ResponseEntity<List<PostcodeRangeDto>> getPostcodeByRange(
			@Valid @RequestParam(required = true) @NonNull String startPostCode, 
			@Valid @RequestParam(required = true) @NonNull String endPostCode
			) {
		return new ResponseEntity<>(
				postcodeService.findByPostcodeBetween(startPostCode, endPostCode),
				HttpStatus.OK);
	}
}
