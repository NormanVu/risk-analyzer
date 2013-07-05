package com.danielpacak.riskanalyzer.frontend.repository.google;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import com.danielpacak.riskanalyzer.domain.DistributionChannel;
import com.danielpacak.riskanalyzer.frontend.repository.DistributionChannelRepository;

public class DistributionChannelRepositoryGoogleImpl implements DistributionChannelRepository {

	private GoogleDatastoreTemplate datastoreTemplate;

	public DistributionChannelRepositoryGoogleImpl(GoogleDatastoreTemplate datastoreTemplate) {
		this.datastoreTemplate = datastoreTemplate;
	}

	@CacheEvict(value = "distribution-channel", key = "#distributionChannel.id", condition = "#distributionChannel.id != null")
	public DistributionChannel save(final DistributionChannel distributionChannel, final String sourceId, final String targetId) {
		return datastoreTemplate.put(distributionChannel, new DistributionChannelWriteConverter(sourceId, targetId));
	}

	@CacheEvict(value = "distribution-channel")
	public void delete(String channelId) {
		datastoreTemplate.delete(DistributionChannel.class, channelId);
	}

	@Cacheable(value = "distribution-channel")
	public DistributionChannel findOne(String channelId) {
		return datastoreTemplate.findById(channelId, DistributionChannel.class, new DistributionChannelReadConverter(
				datastoreTemplate));
	}

	public List<DistributionChannel> findAll() {
		return datastoreTemplate.findAll(DistributionChannel.class, new DistributionChannelReadConverter(
				datastoreTemplate));
	}

}
