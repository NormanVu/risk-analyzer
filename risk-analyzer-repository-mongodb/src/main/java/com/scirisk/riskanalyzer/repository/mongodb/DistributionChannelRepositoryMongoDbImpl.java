package com.scirisk.riskanalyzer.repository.mongodb;

import java.util.List;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.scirisk.riskanalyzer.domain.DistributionChannel;
import com.scirisk.riskanalyzer.repository.DistributionChannelRepository;
import com.scirisk.riskanalyzer.repository.FacilityRepository;

public class DistributionChannelRepositoryMongoDbImpl implements DistributionChannelRepository {

	private MongoTemplate mongoTemplate;

	public DistributionChannelRepositoryMongoDbImpl(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	public DistributionChannel save(DistributionChannel channel, final String sourceId, final String targetId) {
		return mongoTemplate.insert(Collection.distributionChannels.name(), channel,
				new EntityMapper<DistributionChannel>() {
					@Override
					public DBObject map(DistributionChannel entity) {
						BasicDBObject edgeObject = new BasicDBObject();
						edgeObject.put("purchasingVolume", entity.getPurchasingVolume());
						edgeObject.put("source", new ReferenceId(Collection.facilities.name(), sourceId));
						edgeObject.put("target", new ReferenceId(Collection.facilities.name(), targetId));
						return edgeObject;
					}
				});
	}

	public void delete(String distributionChannelId) {
		mongoTemplate.delete(Collection.distributionChannels.name(), distributionChannelId);
	}

	public DistributionChannel findOne(String distributionChannelId) {
		return mongoTemplate.findOne(Collection.distributionChannels.name(), distributionChannelId, documentMapper);
	}

	public List<DistributionChannel> findAll() {
		return mongoTemplate.findAll(Collection.distributionChannels.name(), documentMapper);
	}

	DocumentMapper<DistributionChannel> documentMapper = new DocumentMapper<DistributionChannel>() {
		@Override
		public DistributionChannel map(DBObject document) {
			DistributionChannel channel = new DistributionChannel();
			ObjectId id = (ObjectId) document.get("_id");
			ReferenceId source = new ReferenceId((DBObject) document.get("source"));
			ReferenceId target = new ReferenceId((DBObject) document.get("target"));

			channel.setId(id.toString());
			channel.setPurchasingVolume((Double) document.get("purchasingVolume"));

			FacilityRepository nodeManager = new FacilityRepositoryMongoDbImpl(mongoTemplate); // FIXME
			channel.setSource(nodeManager.findOne(source.getId()));
			channel.setTarget(nodeManager.findOne(target.getId()));
			return channel;
		}
	};

	boolean isBlank(String string) {
		return "".equals(string);
	}

}
