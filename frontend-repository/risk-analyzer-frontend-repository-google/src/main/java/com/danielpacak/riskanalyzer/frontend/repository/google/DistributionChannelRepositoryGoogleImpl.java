package com.danielpacak.riskanalyzer.frontend.repository.google;

import java.util.List;

import com.danielpacak.riskanalyzer.domain.DistributionChannel;
import com.danielpacak.riskanalyzer.frontend.repository.DistributionChannelRepository;

public class DistributionChannelRepositoryGoogleImpl implements DistributionChannelRepository {

	private GoogleDatastoreTemplate datastoreTemplate;

	public DistributionChannelRepositoryGoogleImpl(GoogleDatastoreTemplate datastoreTemplate) {
		this.datastoreTemplate = datastoreTemplate;
	}

	public DistributionChannel save(final DistributionChannel channel, final String sourceId, final String targetId) {
		return datastoreTemplate.put(channel, new DistributionChannelWriteConverter(sourceId, targetId));
	}

	public void delete(String channelId) {
		datastoreTemplate.delete(DistributionChannel.class, channelId);
	}

	public DistributionChannel findOne(String channelId) {
		return datastoreTemplate.findById(channelId, DistributionChannel.class, new DistributionChannelReadConverter(
				datastoreTemplate));
	}

	public List<DistributionChannel> findAll() {
		return datastoreTemplate.findAll(DistributionChannel.class, new DistributionChannelReadConverter(
				datastoreTemplate));
	}

}
