package com.scirisk.riskanalyzer.backend.service;

import com.scirisk.riskanalyzer.backend.domain.CalculateRequest;
import com.scirisk.riskanalyzer.backend.domain.CalculateResponse;

public interface CalculationService {

  CalculateResponse calculate(CalculateRequest request);

}
