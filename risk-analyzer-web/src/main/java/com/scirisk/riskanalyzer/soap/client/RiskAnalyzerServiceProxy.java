package com.scirisk.riskanalyzer.soap.client;

public interface RiskAnalyzerServiceProxy {

  CalculateResponse calculate(CalculateRequest request) throws Exception;

}
