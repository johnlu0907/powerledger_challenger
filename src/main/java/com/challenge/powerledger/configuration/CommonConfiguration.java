package com.challenge.powerledger.configuration;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.*;

@Configuration
public class CommonConfiguration {
	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelmapper = new ModelMapper();
		modelmapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		
		return modelmapper;
	}
}
