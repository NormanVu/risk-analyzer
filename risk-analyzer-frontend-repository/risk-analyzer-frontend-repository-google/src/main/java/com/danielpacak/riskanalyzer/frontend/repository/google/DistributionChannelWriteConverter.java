package com.danielpacak.riskanalyzer.frontend.repository.google;

import static com.danielpacak.riskanalyzer.frontend.repository.google.GoogleDatastoreTemplate.DEFAULT_ENTITY_NAME_STRATEGY;

import org.springframework.util.StringUtils;

import com.danielpacak.riskanalyzer.domain.DistributionChannel;
import com.google.appengine.api.datastore.Entity;

public class DistributionChannelWriteConverter implements
		GoogleDatastoreTemplate.Converter<DistributionChannel, Entity> {
	private String sourceId;
	private String targetId;

	public DistributionChannelWriteConverter(String sourceId, String targetId) {
		this.sourceId = sourceId;
		this.targetId = targetId;
	}

	@Override
	public Entity convert(DistributionChannel channel) {
		// FIXME LOOKS BAD AS I HAVE TO KNOW INTERNAL STUFF SUCH AS
		// ENTITY'S NAME
		final String entityName = DEFAULT_ENTITY_NAME_STRATEGY.getName(channel.getClass());
		Entity entity = StringUtils.hasText(channel.getId()) ? new Entity(entityName, Long.valueOf(channel.getId()))
				: new Entity(entityName);

		entity.setProperty("purchasingVolume", channel.getPurchasingVolume());
		entity.setProperty("sourceId", sourceId);
		entity.setProperty("targetId", targetId);
		return entity;
	}

}
