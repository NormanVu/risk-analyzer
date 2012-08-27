package com.scirisk.riskanalyzer.web.bean;

import java.util.List;

import com.scirisk.riskanalyzer.domain.NetworkEdge;
import com.scirisk.riskanalyzer.domain.NetworkNode;

public class NetworkEdgeFormBean {

	private Long id;
	private Long sourceId;
	private Long targetId;
	private Double purchasingVolume;
	private List<NetworkNode> nodes;

	public NetworkEdgeFormBean() {

	}

	public NetworkEdge getNetworkEdge() {
		NetworkEdge edge = new NetworkEdge();
		edge.setId(id);
		edge.setPurchasingVolume(purchasingVolume);
		return edge;
	}

	public NetworkEdgeFormBean(NetworkEdge edge, List<NetworkNode> nodes) {
		this.id = edge.getId();
		this.sourceId = edge.getSource().getId();
		this.targetId = edge.getTarget().getId();
		this.purchasingVolume = edge.getPurchasingVolume();
		this.nodes = nodes;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSourceId() {
		return sourceId;
	}

	public void setSourceId(Long sourceId) {
		this.sourceId = sourceId;
	}

	public Long getTargetId() {
		return targetId;
	}

	public void setTargetId(Long targetId) {
		this.targetId = targetId;
	}

	public Double getPurchasingVolume() {
		return purchasingVolume;
	}

	public void setPurchasingVolume(Double purchasingVolume) {
		this.purchasingVolume = purchasingVolume;
	}

	public List<NetworkNode> getNodes() {
		return nodes;
	}

}