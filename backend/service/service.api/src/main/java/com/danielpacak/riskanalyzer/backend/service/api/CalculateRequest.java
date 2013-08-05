package com.danielpacak.riskanalyzer.backend.service.api;

import java.io.Serializable;
import java.util.Map;

import com.danielpacak.riskanalyzer.domain.DistributionNetwork;

/**
 * Frequency distribution request model class.
 */
public class CalculateRequest implements Serializable {

	private static final long serialVersionUID = 3329747772574547237L;

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
