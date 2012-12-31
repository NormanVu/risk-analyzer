package com.danielpacak.riskanalyzer.frontend.repository.mongodb;

import com.danielpacak.riskanalyzer.domain.Facility;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * Converts a facility entity to a document that can be saved to the database.
 */
public class FacilityWriteConverter implements Converter<Facility, DBObject> {

	@Override
	public DBObject convert(Facility facility) {
		BasicDBObject dbObject = new BasicDBObject();
		dbObject.put("kind", facility.getKind().toString());
		dbObject.put("type", facility.getType().toString());
		dbObject.put("name", facility.getName());
		dbObject.put("description", facility.getDescription());

		dbObject.put("address", facility.getAddress());
		dbObject.put("latitude", facility.getLatitude());
		dbObject.put("longitude", facility.getLongitude());

		dbObject.put("riskCategory1", facility.getRiskCategory1());
		dbObject.put("riskCategory2", facility.getRiskCategory2());
		dbObject.put("riskCategory3", facility.getRiskCategory3());

		dbObject.put("recoveryTime1", facility.getRecoveryTime1());
		dbObject.put("recoveryTime2", facility.getRecoveryTime2());
		dbObject.put("recoveryTime3", facility.getRecoveryTime3());
		return dbObject;
	}

}
