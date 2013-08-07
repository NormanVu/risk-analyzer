package com.danielpacak.riskanalyzer.backend.service.api;

import java.io.Serializable;

import com.danielpacak.riskanalyzer.domain.DistributionNetwork;

/**
 * Frequency distribution request model class.
 */
public class CalculateRequest implements Serializable {

	private static final long serialVersionUID = 3329747772574547237L;

	private DistributionNetwork network;

	private Long numberOfIterations;

	private Long timeHorizon;

	private Float confidenceLevel;

	public DistributionNetwork getNetwork() {
		return network;
	}

	public CalculateRequest setNetwork(DistributionNetwork network) {
		this.network = network;
		return this;
	}

	public Long getNumberOfIterations() {
		return numberOfIterations;
	}

	public CalculateRequest setNumberOfIterations(Long numberOfIterations) {
		this.numberOfIterations = numberOfIterations;
		return this;
	}

	public Long getTimeHorizon() {
		return timeHorizon;
	}

	public CalculateRequest setTimeHorizon(Long timeHorizon) {
		this.timeHorizon = timeHorizon;
		return this;
	}

	public Float getConfidenceLevel() {
		return confidenceLevel;
	}

	public CalculateRequest setConfidenceLevel(Float confidenceLevel) {
		this.confidenceLevel = confidenceLevel;
		return this;
	}

}
