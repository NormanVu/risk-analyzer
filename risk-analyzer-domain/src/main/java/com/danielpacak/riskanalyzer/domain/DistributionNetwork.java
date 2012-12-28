package com.danielpacak.riskanalyzer.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

@SuppressWarnings("serial")
public class DistributionNetwork implements Serializable {

	private Collection<Facility> nodes = new ArrayList<Facility>();
	private Collection<DistributionChannel> edges = new ArrayList<DistributionChannel>();

	public DistributionNetwork() {
	}

	public DistributionNetwork(Collection<Facility> nodes,
			Collection<DistributionChannel> edges) {
		this.nodes = nodes;
		this.edges = edges;
	}

	public Collection<Facility> getNodes() {
		return nodes;
	}

	public void setNodes(Collection<Facility> nodes) {
		this.nodes = nodes;
	}

	public Collection<DistributionChannel> getEdges() {
		return edges;
	}

	public void setEdges(Collection<DistributionChannel> edges) {
		this.edges = edges;
	}

}