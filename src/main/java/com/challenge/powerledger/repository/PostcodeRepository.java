package com.challenge.powerledger.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.challenge.powerledger.model.Postcode;

public interface PostcodeRepository extends JpaRepository<Postcode, Long> {
	List<Postcode> findByPostcodeBetween(String start, String end);
}
