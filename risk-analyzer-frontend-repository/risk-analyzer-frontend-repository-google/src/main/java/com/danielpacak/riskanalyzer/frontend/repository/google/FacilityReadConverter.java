package com.danielpacak.riskanalyzer.frontend.repository.google;

import com.danielpacak.riskanalyzer.domain.Facility;
import com.danielpacak.riskanalyzer.domain.Facility.Kind;
import com.danielpacak.riskanalyzer.domain.Facility.Type;
import com.danielpacak.riskanalyzer.frontend.repository.google.GoogleDatastoreTemplate.Converter;
import com.google.appengine.api.datastore.Entity;

public class FacilityReadConverter implements Converter<Entity, Facility> {

	public Facility convert(Entity entity) {
		Facility facility = new Facility();

		facility.setId(String.valueOf(entity.getKey().getId()));

		facility.setType(convertTypeProperty(entity));
		facility.setKind(convertKindProperty(entity));
		facility.setName((String) entity.getProperty("name"));
		facility.setDescription((String) entity.getProperty("description"));
		facility.setAddress((String) entity.getProperty("address"));
		facility.setLatitude((Double) entity.getProperty("latitude"));
		facility.setLongitude((Double) entity.getProperty("longitude"));

		facility.setRiskCategory1((Double) entity.getProperty("riskCategory1"));
		facility.setRiskCategory2((Double) entity.getProperty("riskCategory2"));
		facility.setRiskCategory3((Double) entity.getProperty("riskCategory3"));

		facility.setRecoveryTime1((Double) entity.getProperty("recoveryTime1"));
		facility.setRecoveryTime2((Double) entity.getProperty("recoveryTime2"));
		facility.setRecoveryTime3((Double) entity.getProperty("recoveryTime3"));
		return facility;
	}

	Type convertTypeProperty(Entity entity) {
		String type = (String) entity.getProperty("type");
		return type != null ? Type.valueOf(type) : null;
	}

	Kind convertKindProperty(Entity entity) {
		String kind = (String) entity.getProperty("kind");
		return kind != null ? Kind.valueOf(kind) : null;
	}

}
