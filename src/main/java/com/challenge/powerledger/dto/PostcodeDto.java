package com.challenge.powerledger.dto;

import javax.validation.constraints.*;

import lombok.*;

@Data
public class PostcodeDto {
	@NotEmpty(message = "name cannot be empty")
	private String name;
	@NotEmpty(message = "postcode cannot be empty")
	private String postcode;
}
