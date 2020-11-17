package com.societe.general.assignment.models;

@Entity
public class ProductMaster {
	@Id
	@GeneratedValue
	private Long id;
	private String name;
	private String description;
	private int price;
	private boolean isAvailable;
}
