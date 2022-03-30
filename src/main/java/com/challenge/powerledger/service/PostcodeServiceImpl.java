package com.challenge.powerledger.service;

import java.util.*;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.challenge.powerledger.dto.PostcodeDto;
import com.challenge.powerledger.dto.PostcodeRangeDto;
import com.challenge.powerledger.model.Postcode;
import com.challenge.powerledger.repository.PostcodeRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PostcodeServiceImpl implements PostcodeService {
	
	@Autowired
	private PostcodeRepository postcodeRepository;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public void saveAll(List<PostcodeDto> postcodeDtos) {
		List<Postcode> postcodes 
			= postcodeDtos
				.stream()
				.map(postcode -> modelMapper.map(postcode, Postcode.class))
				.collect(Collectors.toList());
		log.info("lists {}", postcodeDtos.toString());
		postcodeRepository.saveAll(postcodes);
	}

	@Override
	public List<PostcodeRangeDto> findByPostcodeBetween(String start, String end) {
		List<PostcodeRangeDto> result = new ArrayList<>();
		List<Postcode> postcodes = postcodeRepository.findByPostcodeBetween(start, end);
		
		Map<String, List<Postcode>> group 
					= postcodes
						.stream()
						.collect(Collectors.groupingBy(obj -> obj.getPostcode()));
		group.forEach((k, v) -> {
			PostcodeRangeDto r = new PostcodeRangeDto();
			r.setPostcode(k);
			r.setTotalOfNumber(v.size());
			List<String> values = v.stream().map(Postcode::getName).sorted().collect(Collectors.toList());
			r.setName(values);
			
			result.add(r);
		});
		
		return result;
	}
}
