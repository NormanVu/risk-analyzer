package com.danielpacak.riskanalyzer.frontend.repository.google;

import java.util.List;

import org.springframework.util.StringUtils;

import com.danielpacak.riskanalyzer.domain.DistributionChannel;
import com.danielpacak.riskanalyzer.domain.Facility;
import com.danielpacak.riskanalyzer.frontend.repository.DistributionChannelRepository;
import com.google.appengine.api.datastore.Entity;

public class DistributionChannelRepositoryGoogleImpl implements DistributionChannelRepository {

	private GoogleDatastoreTemplate datastoreTemplate;

	public DistributionChannelRepositoryGoogleImpl(GoogleDatastoreTemplate datastoreTemplate) {
		this.datastoreTemplate = datastoreTemplate;
	}

	public DistributionChannel save(final DistributionChannel channel, final String sourceId, final String targetId) {
		return datastoreTemplate.put(channel, new GoogleDatastoreTemplate.Converter<DistributionChannel, Entity>() {
			@Override
			public Entity convert(DistributionChannel f) {
				// FIXME LOOKS BAD AS I HAVE TO KNOW INTERNAL STUFF SUCH AS
				// ENTITY'S NAME
				Entity entity = StringUtils.hasText(channel.getId()) ? new Entity(DistributionChannel.class.getName(),
						Long.valueOf(channel.getId())) : new Entity(DistributionChannel.class.getName());

				entity.setProperty("purchasingVolume", channel.getPurchasingVolume());
				entity.setProperty("sourceId", sourceId);
				entity.setProperty("targetId", targetId);
				return entity;
			}
		});
	}

	public void delete(String channelId) {
		datastoreTemplate.delete(DistributionChannel.class, channelId);
	}

	public DistributionChannel findOne(String channelId) {
		return datastoreTemplate.findById(channelId, DistributionChannel.class, new DistributionChannelReadConverter());
	}

	public List<DistributionChannel> findAll() {
		return datastoreTemplate.findAll(DistributionChannel.class, new DistributionChannelReadConverter());
	}

	class DistributionChannelReadConverter implements GoogleDatastoreTemplate.Converter<Entity, DistributionChannel> {
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

}
