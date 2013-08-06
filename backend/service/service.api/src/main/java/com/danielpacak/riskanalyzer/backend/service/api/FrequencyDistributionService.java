package com.danielpacak.riskanalyzer.backend.service.api;

public interface FrequencyDistributionService {

	String NUMBER_OF_ITERATIONS = "number_of_iterations";
	String TIME_HORIZON = "time_horizon";
	String CONFIDENCE_LEVEL = "confidence_level";

	CalculateResponse calculate(CalculateRequest request) throws Exception;

}
