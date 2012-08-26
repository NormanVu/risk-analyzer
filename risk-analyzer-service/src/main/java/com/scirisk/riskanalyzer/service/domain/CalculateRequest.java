package com.scirisk.riskanalyzer.service.domain;

import java.io.Serializable;
import java.util.Map;

public class CalculateRequest implements Serializable {

  private Map<String, String> inputParams;

  public Map<String, String> getInputParams() {
    return inputParams;
  }

  public void setInputParams(Map<String, String> inputParams) {
    this.inputParams = inputParams;
  }

}
