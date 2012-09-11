package com.scirisk.riskanalyzer.controller;

import java.util.List;

import com.scirisk.riskanalyzer.domain.DistributionChannel;
import com.scirisk.riskanalyzer.domain.Facility;

public class DistributionChannelFormBean {

	private String id;
	private String sourceId;
	private String targetId;
	private Double purchasingVolume;
	private List<Facility> nodes;

	public DistributionChannelFormBean() {

	}

	public DistributionChannel getNetworkEdge() {
		DistributionChannel edge = new DistributionChannel();
		edge.setId(id);
		edge.setPurchasingVolume(purchasingVolume);
		return edge;
	}

	public DistributionChannelFormBean(DistributionChannel edge, List<Facility> nodes) {
		this.id = edge.getId();
		this.sourceId = edge.getSource().getId();
		this.targetId = edge.getTarget().getId();
		this.purchasingVolume = edge.getPurchasingVolume();
		this.nodes = nodes;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	public String getTargetId() {
		return targetId;
	}

	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}

	public Double getPurchasingVolume() {
		return purchasingVolume;
	}

	public void setPurchasingVolume(Double purchasingVolume) {
		this.purchasingVolume = purchasingVolume;
	}

	public List<Facility> getNodes() {
		return nodes;
	}

}