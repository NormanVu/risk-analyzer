package com.danielpacak.riskanalyzer.frontend.repository.google;

import com.danielpacak.riskanalyzer.domain.DistributionChannel;
import com.danielpacak.riskanalyzer.domain.Facility;
import com.google.appengine.api.datastore.Entity;

public class DistributionChannelReadConverter implements GoogleDatastoreTemplate.Converter<Entity, DistributionChannel> {

	private GoogleDatastoreTemplate datastoreTemplate;

	public DistributionChannelReadConverter(GoogleDatastoreTemplate datastoreTemplate) {
		this.datastoreTemplate = datastoreTemplate;
	}

	@Override
	public DistributionChannel convert(Entity channelEntity) {
		DistributionChannel channel = new DistributionChannel();
		channel.setId(String.valueOf(channelEntity.getKey().getId()));
		channel.setPurchasingVolume((Double) channelEntity.getProperty("purchasingVolume"));

		String sourceId = (String) channelEntity.getProperty("sourceId");
		String targetId = (String) channelEntity.getProperty("targetId");

		Facility sourceFacility = datastoreTemplate.findById(sourceId, Facility.class, new FacilityReadConverter());
		Facility targetFacility = datastoreTemplate.findById(targetId, Facility.class, new FacilityReadConverter());

		channel.setSource(sourceFacility);
		channel.setTarget(targetFacility);

		return channel;
	}

}
