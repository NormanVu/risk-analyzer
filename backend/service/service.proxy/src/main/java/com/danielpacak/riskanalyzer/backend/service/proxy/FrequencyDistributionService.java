package com.danielpacak.riskanalyzer.backend.service.proxy;

public interface FrequencyDistributionService {

	CalculateResponse calculate(CalculateRequest request) throws Exception;

}
