package com.danielpacak.riskanalyzer.frontend.web.form;

import java.io.Serializable;
import java.util.List;

import com.danielpacak.riskanalyzer.domain.DistributionChannel;
import com.danielpacak.riskanalyzer.domain.Facility;

public class DistributionChannelForm implements Serializable {

	private static final long serialVersionUID = 4901990226773004944L;

	private String id;
	private String sourceId;
	private String targetId;
	private Double purchasingVolume;
	private List<Facility> facilities;

	public DistributionChannelForm() {

	}

	public DistributionChannelForm(DistributionChannel distributionChannel, List<Facility> facilities) {
		this.id = distributionChannel.getId();
		this.sourceId = distributionChannel.getSource().getId();
		this.targetId = distributionChannel.getTarget().getId();
		this.purchasingVolume = distributionChannel.getPurchasingVolume();
		this.facilities = facilities;
	}

	public DistributionChannel getDistributionChannel() {
		DistributionChannel edge = new DistributionChannel();
		edge.setId(id);
		edge.setPurchasingVolume(purchasingVolume);
		return edge;
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

	public List<Facility> getFacilities() {
		return facilities;
	}

}
