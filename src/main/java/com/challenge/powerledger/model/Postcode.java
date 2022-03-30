package com.challenge.powerledger.model;

import javax.persistence.*;

import lombok.*;

@Entity
@Table(name = "postcode")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Postcode {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Column(name = "name", nullable = false)
	private String name;
	@Column(name = "postcode", nullable = false)
	private String postcode;
}
