package com.challenge.powerledger.dto;

import java.util.List;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PostcodeRangeDto {
	private String postcode;
	private int totalOfNumber;
	private List<String> name;
}
