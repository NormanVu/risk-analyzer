package com.scirisk.riskanalyzer.domain;

import java.io.Serializable;

// TODO rename to DistributionChannel
@SuppressWarnings("serial")
public class NetworkEdge implements Serializable {

	private String id;
	private Double purchasingVolume;
	private NetworkNode source;
	private NetworkNode target;

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

	public NetworkNode getSource() {
		return source;
	}

	public void setSource(NetworkNode source) {
		this.source = source;
	}

	public NetworkNode getTarget() {
		return target;
	}

	public void setTarget(NetworkNode target) {
		this.target = target;
	}

}