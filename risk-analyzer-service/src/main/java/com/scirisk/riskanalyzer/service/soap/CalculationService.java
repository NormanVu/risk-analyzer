package com.scirisk.riskanalyzer.service.soap;

import com.scirisk.riskanalyzer.service.domain.CalculateRequest;
import com.scirisk.riskanalyzer.service.domain.CalculateResponse;

public interface CalculationService {

  CalculateResponse calculate(CalculateRequest request);

}
