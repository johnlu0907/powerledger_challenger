package com.challenge.powerledger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

import java.util.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.challenge.powerledger.controller.PostcodeController;
import com.challenge.powerledger.model.Postcode;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
class PowerledgerApplicationTests {

	@Autowired
	private PostcodeController controller;
	
	@Autowired
	private MockMvc mockMvc;
	
    @Autowired
    ObjectMapper mapper;
    
	@Test
	void contextLoads() {
		assertThat(controller).isNotNull();
	}

	@Test
	@DisplayName("/insert should insert postcode object to db and returns 201 status code")
	public void insert_whenInsertPostcodeObject_thenStatus201() throws Exception {
		List<Postcode> postcodes
			= Arrays.asList(
					Postcode.builder()
								.name("test")
								.postcode("123")
								.build(),
					Postcode.builder()
								.name("test1")
								.postcode("123")
								.build(),
					Postcode.builder()
								.name("test3")
								.postcode("1234")
								.build()
					);
		this.mockMvc
				.perform(
						post("/api/insert")
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON)
						.content(this.mapper.writeValueAsBytes(postcodes))
				)
				.andDo(print())
				.andExpect(status().isCreated())
				.andExpect(content().string("Inserted postcode successfully"));
	}
	
	@Test
	@DisplayName("/insert if inserts postcode object to db with an empty request object and returns 4xx status code and display a message as like  postcode list cannot be empty.")
	public void insert_whenInsertPostcodeObjectWithEmptyObject_thenStatus4xx() throws Exception {
		List<Postcode> postcodes = new ArrayList<>();
		this.mockMvc
				.perform(
						post("/api/insert")
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON)
						.content(this.mapper.writeValueAsBytes(postcodes))
				)
				.andDo(print())
				.andExpect(status().is4xxClientError())
				.andExpect(content().string(" postcode list cannot be empty."));
	}
	
	@Test
	@DisplayName("/insert if inserts postcode object to db with a missed property and returns 4xx status code and display a message as like name cannot be empty")
	public void insert_whenInsertPostcodeObjectWithMissedProperty_thenStatus4xx() throws Exception {
		List<Postcode> postcodes
			= Arrays.asList(
					Postcode.builder()
								.postcode("123")
								.build()
					);
		this.mockMvc
				.perform(
						post("/api/insert")
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON)
						.content(this.mapper.writeValueAsBytes(postcodes))
				)
				.andDo(print())
				.andExpect(status().is4xxClientError())
				.andExpect(content().string(" name cannot be empty"));
	}
	
	@Test
	@DisplayName("/list should return body the list of names belonging to that postcode range, sorted alphabetically as well as the total number of characters of all names combined and returns 200 status code")
	public void getPostcodesByRange_whenCallApi_thenReturnsListOfPostcodeObjectAndStatus200() throws Exception {
		this.mockMvc
					.perform(
							get("/api/list")
								.contentType(MediaType.APPLICATION_JSON)
								.param("startPostCode", "1")
								.param("endPostCode", "2")
					)
					.andDo(print())
			      	.andExpect(status().isOk());
	}
	
	@Test
	@DisplayName("/list should returns 4xx status code and message as like error : endPostCode parameter is required")
	public void getPostcodesByRange_whenCallApiWithMissedParameter_thenReturnsStatus4xx() throws Exception {
		this.mockMvc
					.perform(
							get("/api/list")
								.contentType(MediaType.APPLICATION_JSON)
								.param("startPostCode", "1")
					)
					.andDo(print())
			      	.andExpect(status().is4xxClientError())
			      	.andExpect(jsonPath("error").value("endPostCode parameter is required"));
	}
}
