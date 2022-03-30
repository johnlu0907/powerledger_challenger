package com.challenge.powerledger.service;

import java.util.List;

import com.challenge.powerledger.dto.*;

public interface PostcodeService {
	public abstract void saveAll(List<PostcodeDto> postcodeDtos);
	public abstract List<PostcodeRangeDto> findByPostcodeBetween(String start, String end);
}
