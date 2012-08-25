package com.scirisk.riskanalyzer.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
// TODO rename to DistributionChannel
@Entity()
@SuppressWarnings("serial")
public class NetworkEdge implements Serializable {

	private Long id;
	private Double purchasingVolume;
	private NetworkNode source;
	private NetworkNode target;

	@Id()
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getPurchasingVolume() {
		return purchasingVolume;
	}

	public void setPurchasingVolume(Double purchasingVolume) {
		this.purchasingVolume = purchasingVolume;
	}

	@OneToOne()
	public NetworkNode getSource() {
		return source;
	}

	public void setSource(NetworkNode source) {
		this.source = source;
	}

	@OneToOne()
	public NetworkNode getTarget() {
		return target;
	}

	public void setTarget(NetworkNode target) {
		this.target = target;
	}

}