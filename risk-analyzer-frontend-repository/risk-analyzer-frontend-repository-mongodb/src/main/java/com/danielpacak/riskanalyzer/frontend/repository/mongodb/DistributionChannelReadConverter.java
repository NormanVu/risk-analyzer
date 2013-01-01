package com.danielpacak.riskanalyzer.frontend.repository.mongodb;

import org.bson.types.ObjectId;

import com.danielpacak.riskanalyzer.domain.DistributionChannel;
import com.danielpacak.riskanalyzer.domain.Facility;
import com.danielpacak.riskanalyzer.frontend.repository.mongodb.MongoTemplate.Converter;
import com.mongodb.DBObject;

public class DistributionChannelReadConverter implements Converter<DBObject, DistributionChannel> {

	private MongoTemplate mongoTemplate;

	public DistributionChannelReadConverter(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	@Override
	public DistributionChannel convert(DBObject document) {
		DistributionChannel channel = new DistributionChannel();
		ObjectId id = (ObjectId) document.get("_id");
		ReferenceId source = new ReferenceId((DBObject) document.get("source"));
		ReferenceId target = new ReferenceId((DBObject) document.get("target"));

		Facility sourceFacility = mongoTemplate.findById(source.getId(), Facility.class, new FacilityReadConverter());
		Facility targetFacility = mongoTemplate.findById(target.getId(), Facility.class, new FacilityReadConverter());

		channel.setId(id.toString());
		channel.setPurchasingVolume((Double) document.get("purchasingVolume"));
		channel.setSource(sourceFacility);
		channel.setTarget(targetFacility);
		return channel;
	}

}
