package com.danielpacak.riskanalyzer.backend.service.api;

public interface FrequencyDistributionService {

	CalculateResponse calculate(CalculateRequest request) throws Exception;

}
