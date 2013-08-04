package com.danielpacak.riskanalyzer.frontend.repository.mongodb;

import java.util.List;

import com.danielpacak.riskanalyzer.domain.DistributionChannel;
import com.danielpacak.riskanalyzer.frontend.repository.api.DistributionChannelRepository;

public class DistributionChannelRepositoryMongoDbImpl implements DistributionChannelRepository {

	private MongoTemplate mongoTemplate;

	public DistributionChannelRepositoryMongoDbImpl(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	public DistributionChannel save(DistributionChannel channel, final String sourceId, final String targetId) {
		return mongoTemplate.save(channel, new DistributionChannelWriteConverter(sourceId, targetId));
	}

	public void delete(String distributionChannelId) {
		mongoTemplate.delete(DistributionChannel.class, distributionChannelId);
	}

	public DistributionChannel findOne(String distributionChannelId) {
		return mongoTemplate.findById(distributionChannelId, DistributionChannel.class,
				new DistributionChannelReadConverter(mongoTemplate));
	}

	public List<DistributionChannel> findAll() {
		return mongoTemplate.findAll(DistributionChannel.class, new DistributionChannelReadConverter(mongoTemplate));
	}

}
