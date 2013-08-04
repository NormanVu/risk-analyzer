package com.danielpacak.riskanalyzer.frontend.repository.api;

@SuppressWarnings("serial")
public class EntityNotFoundException extends RuntimeException {

	public EntityNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

}
