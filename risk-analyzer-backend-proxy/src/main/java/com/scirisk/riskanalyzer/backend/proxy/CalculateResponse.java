package com.scirisk.riskanalyzer.backend.proxy;

import java.util.List;
import java.util.Map;

public class CalculateResponse {

  private List<double[]> frequencyDistribution;
  private Map<String, String> inputParams;
  private Map<String, String> outputParams;

  public List<double[]> getFrequencyDistribution() {
    return frequencyDistribution;
  }

  public void setFrequencyDistribution(List<double[]> frequencyDistribution) {
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
