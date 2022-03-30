package com.challenge.powerledger.validation;

import java.util.*;
import java.util.List;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class ErrorResponse<T> {
	private List<T> error = new ArrayList<>(1);
	   
	public ErrorResponse(List<T> error) {
		this.error = error;
	}
 
	public List<T> getError() {
		return error;
	}
}
