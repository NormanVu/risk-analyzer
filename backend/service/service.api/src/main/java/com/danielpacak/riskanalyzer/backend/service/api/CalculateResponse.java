package com.danielpacak.riskanalyzer.backend.service.api;

import java.io.Serializable;
import java.util.Map;

/**
 * Frequency distribution response model class.
 */
public class CalculateResponse implements Serializable {

	private static final long serialVersionUID = 2125376804888519918L;

	private double[][] frequencyDistribution;

	private Long numberOfIterations;

	private Long timeHorizon;

	private Float confidenceLevel;

	private Map<String, String> outputParams;

	public double[][] getFrequencyDistribution() {
		return frequencyDistribution;
	}

	public CalculateResponse setFrequencyDistribution(double[][] frequencyDistribution) {
		this.frequencyDistribution = frequencyDistribution;
		return this;
	}

	public Long getNumberOfIterations() {
		return numberOfIterations;
	}

	public CalculateResponse setNumberOfIterations(Long numberOfIterations) {
		this.numberOfIterations = numberOfIterations;
		return this;
	}

	public Long getTimeHorizon() {
		return timeHorizon;
	}

	public CalculateResponse setTimeHorizon(Long timeHorizon) {
		this.timeHorizon = timeHorizon;
		return this;
	}

	public Float getConfidenceLevel() {
		return confidenceLevel;
	}

	public CalculateResponse setConfidenceLevel(Float confidenceLevel) {
		this.confidenceLevel = confidenceLevel;
		return this;
	}

	public Map<String, String> getOutputParams() {
		return outputParams;
	}

	public CalculateResponse setOutputParams(Map<String, String> outputParams) {
		this.outputParams = outputParams;
		return this;
	}

}
