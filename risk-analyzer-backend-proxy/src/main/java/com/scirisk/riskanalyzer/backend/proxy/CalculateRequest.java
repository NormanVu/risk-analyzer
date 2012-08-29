package com.scirisk.riskanalyzer.backend.proxy;

import java.util.Map;

import com.scirisk.riskanalyzer.domain.Network;

public class CalculateRequest {

  private Network network;
  private Map<String, String> inputParams;

  public Network getNetwork() {
    return network;
  }

  public void setNetwork(Network network) {
    this.network = network;
  }

  public Map<String, String> getInputParams() {
    return inputParams;
  }

  public void setInputParams(Map<String, String> inputParams) {
    this.inputParams = inputParams;
  }

}
