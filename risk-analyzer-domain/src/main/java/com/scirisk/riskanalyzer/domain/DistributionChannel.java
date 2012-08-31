package com.scirisk.riskanalyzer.domain;

import java.io.Serializable;

@SuppressWarnings("serial")
public class DistributionChannel implements Serializable {

	private String id;
	private Double purchasingVolume;
	private Facility source;
	private Facility target;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Double getPurchasingVolume() {
		return purchasingVolume;
	}

	public void setPurchasingVolume(Double purchasingVolume) {
		this.purchasingVolume = purchasingVolume;
	}

	public Facility getSource() {
		return source;
	}

	public void setSource(Facility source) {
		this.source = source;
	}

	public Facility getTarget() {
		return target;
	}

	public void setTarget(Facility target) {
		this.target = target;
	}

}