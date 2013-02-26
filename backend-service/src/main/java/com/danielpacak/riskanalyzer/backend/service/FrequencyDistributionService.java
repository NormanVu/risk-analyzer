package com.danielpacak.riskanalyzer.backend.service;

public interface FrequencyDistributionService {

	CalculateResponse calculate(CalculateRequest request) throws Exception;

}
