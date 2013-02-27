package com.danielpacak.riskanalyzer.frontend.web.controller;

public class FrequencyDistributionParameters {
	private Float confidenceLevel;
	private String endpointUrl;
	private Long numberOfIterations;
	private Integer timeHorizon;

	public Float getConfidenceLevel() {
		return confidenceLevel;
	}

	public void setConfidenceLevel(Float confidenceLevel) {
		this.confidenceLevel = confidenceLevel;
	}

	public String getEndpointUrl() {
		return endpointUrl;
	}

	public void setEndpointUrl(String endpointUrl) {
		this.endpointUrl = endpointUrl;
	}

	public Long getNumberOfIterations() {
		return numberOfIterations;
	}

	public void setNumberOfIterations(Long numberOfIterations) {
		this.numberOfIterations = numberOfIterations;
	}

	public Integer getTimeHorizon() {
		return timeHorizon;
	}

	public void setTimeHorizon(Integer timeHorizon) {
		this.timeHorizon = timeHorizon;
	}

}
