package com.danielpacak.riskanalyzer.backend.service;

import java.util.Map;

public class CalculateResponse {

  private double[][] frequencyDistribution;
  private Map<String, String> inputParams;
  private Map<String, String> outputParams;

  public double[][] getFrequencyDistribution() {
    return frequencyDistribution;
  }

  public void setFrequencyDistribution(double[][] frequencyDistribution) {
    this.frequencyDistribution = frequencyDistribution;
  }

  public Map<String, String> getInputParams() {
    return inputParams;
  }

  public void setInputParams(Map<String, String> inputParams) {
    this.inputParams = inputParams;
  }

  public Map<String, String> getOutputParams() {
    return outputParams;
  }

  public void setOutputParams(Map<String, String> outputParams) {
    this.outputParams = outputParams;
  }

}
