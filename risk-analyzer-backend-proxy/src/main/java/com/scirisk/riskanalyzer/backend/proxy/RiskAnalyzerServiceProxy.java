package com.scirisk.riskanalyzer.backend.proxy;

public interface RiskAnalyzerServiceProxy {

  CalculateResponse calculate(CalculateRequest request) throws Exception;

}
