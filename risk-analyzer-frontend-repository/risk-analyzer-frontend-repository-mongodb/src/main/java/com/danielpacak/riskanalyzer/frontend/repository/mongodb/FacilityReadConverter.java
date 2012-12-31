package com.danielpacak.riskanalyzer.frontend.repository.mongodb;

import org.bson.types.ObjectId;

import com.danielpacak.riskanalyzer.domain.Facility;
import com.danielpacak.riskanalyzer.domain.Facility.Kind;
import com.danielpacak.riskanalyzer.domain.Facility.Type;
import com.mongodb.DBObject;

/**
 * Converts a document saved in the database to a facility entity.
 */
public class FacilityReadConverter implements Converter<DBObject, Facility> {

	@Override
	public Facility convert(DBObject document) {
		Facility facility = new Facility();
		ObjectId id = (ObjectId) document.get("_id");

		facility.setId(id.toString());
		facility.setType(Type.valueOf((String) document.get("type")));
		facility.setKind(Kind.valueOf((String) document.get("kind")));
		facility.setName((String) document.get("name"));
		facility.setDescription((String) document.get("description"));
		facility.setAddress((String) document.get("address"));
		facility.setLatitude((Double) document.get("latitude"));
		facility.setLongitude((Double) document.get("longitude"));

		facility.setRiskCategory1((Double) document.get("riskCategory1"));
		facility.setRiskCategory2((Double) document.get("riskCategory2"));
		facility.setRiskCategory3((Double) document.get("riskCategory3"));

		facility.setRecoveryTime1((Double) document.get("recoveryTime1"));
		facility.setRecoveryTime2((Double) document.get("recoveryTime2"));
		facility.setRecoveryTime3((Double) document.get("recoveryTime3"));

		return facility;
	}

}
