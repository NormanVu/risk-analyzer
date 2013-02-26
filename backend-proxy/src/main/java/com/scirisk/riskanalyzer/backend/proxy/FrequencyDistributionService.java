package com.scirisk.riskanalyzer.backend.proxy;


public interface FrequencyDistributionService {

	CalculateResponse calculate(CalculateRequest request) throws Exception;

}
