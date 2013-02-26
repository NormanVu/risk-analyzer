package com.danielpacak.riskanalyzer.domain;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@SuppressWarnings("serial")
public class DistributionNetwork implements Serializable {

	private Collection<Facility> facilities;
	private Collection<DistributionChannel> channels;

	public DistributionNetwork() {
	}

	public DistributionNetwork(List<Facility> facilities, List<DistributionChannel> channels) {
		this.facilities = facilities;
		this.channels = channels;
	}

	public Collection<Facility> getNodes() {
		return facilities;
	}

	public Collection<DistributionChannel> getEdges() {
		return channels;
	}

}
