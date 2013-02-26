package com.scirisk.riskanalyzer.backend.service;

import java.io.Serializable;
import java.util.Map;

import com.danielpacak.riskanalyzer.domain.DistributionNetwork;

public class CalculateRequest implements Serializable {

	private DistributionNetwork network;
	private Map<String, String> inputParams;

	public Map<String, String> getInputParams() {
		return inputParams;
	}

	public void setInputParams(Map<String, String> inputParams) {
		this.inputParams = inputParams;
	}

	public DistributionNetwork getNetwork() {
		return network;
	}

	public void setNetwork(DistributionNetwork network) {
		this.network = network;
	}

}
