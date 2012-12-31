package com.danielpacak.riskanalyzer.frontend.repository.mongodb;

import com.danielpacak.riskanalyzer.domain.DistributionChannel;
import com.danielpacak.riskanalyzer.domain.Facility;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class DistributionChannelWriteConverter implements Converter<DistributionChannel, DBObject> {

	private String sourceId;
	private String targetId;

	public DistributionChannelWriteConverter(String sourceId, String targetId) {
		this.sourceId = sourceId;
		this.targetId = targetId;
	}

	@Override
	public DBObject convert(DistributionChannel channel) {
		BasicDBObject dbObject = new BasicDBObject();
		dbObject.put("purchasingVolume", channel.getPurchasingVolume());
		dbObject.put("source", new ReferenceId<Facility>(Facility.class, sourceId));
		dbObject.put("target", new ReferenceId<Facility>(Facility.class, targetId));
		return dbObject;
	}

}
