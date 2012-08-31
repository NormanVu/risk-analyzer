package com.scirisk.riskanalyzer.backend.proxy;

import java.util.Map;

import com.scirisk.riskanalyzer.domain.DistributionNetwork;

public class CalculateRequest {

  private DistributionNetwork network;
  private Map<String, String> inputParams;

  public DistributionNetwork getNetwork() {
    return network;
  }

  public void setNetwork(DistributionNetwork network) {
    this.network = network;
  }

  public Map<String, String> getInputParams() {
    return inputParams;
  }

  public void setInputParams(Map<String, String> inputParams) {
    this.inputParams = inputParams;
  }

}
