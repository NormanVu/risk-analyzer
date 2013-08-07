package com.danielpacak.riskanalyzer.frontend.web.form;

import java.io.Serializable;

public class FrequencyDistributionParametersForm implements Serializable {

	private static final long serialVersionUID = 2066170828755849530L;

	private Float confidenceLevel;

	private Long numberOfIterations;

	private Long timeHorizon;

	public Float getConfidenceLevel() {
		return confidenceLevel;
	}

	public FrequencyDistributionParametersForm setConfidenceLevel(Float confidenceLevel) {
		this.confidenceLevel = confidenceLevel;
		return this;
	}

	public Long getNumberOfIterations() {
		return numberOfIterations;
	}

	public FrequencyDistributionParametersForm setNumberOfIterations(Long numberOfIterations) {
		this.numberOfIterations = numberOfIterations;
		return this;
	}

	public Long getTimeHorizon() {
		return timeHorizon;
	}

	public FrequencyDistributionParametersForm setTimeHorizon(Long timeHorizon) {
		this.timeHorizon = timeHorizon;
		return this;
	}

}
