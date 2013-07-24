package com.example.calendar.web.model;

import org.hibernate.validator.constraints.NotEmpty;

public class CreateRepoForm {

	@NotEmpty(message = "Name is required")
	private String name;
	@NotEmpty(message = "Description is required")
	private String description;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
