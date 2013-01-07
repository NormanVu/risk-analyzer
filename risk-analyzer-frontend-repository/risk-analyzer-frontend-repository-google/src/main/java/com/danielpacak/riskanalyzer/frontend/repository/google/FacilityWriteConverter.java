package com.danielpacak.riskanalyzer.frontend.repository.google;

import org.springframework.util.StringUtils;

import com.danielpacak.riskanalyzer.domain.Facility;
import com.danielpacak.riskanalyzer.frontend.repository.google.GoogleDatastoreTemplate.Converter;
import com.google.appengine.api.datastore.Entity;

/** Converts facility entity to the google datastore entity. */
public class FacilityWriteConverter implements Converter<Facility, Entity> {

	@Override
	public Entity convert(Facility facility) {
		// FIXME CONVERTED SHOULDN'T KNOW ABOUT ENTITY NAME
		Entity entity = StringUtils.hasText(facility.getId()) ? new Entity(Facility.class.getName(),
				Long.valueOf(facility.getId())) : new Entity(Facility.class.getName());

		entity.setProperty("type", facility.getType().name());
		entity.setProperty("kind", facility.getKind().name());
		entity.setProperty("name", facility.getName());
		entity.setProperty("description", facility.getDescription());
		entity.setProperty("address", facility.getAddress());
		entity.setProperty("latitude", facility.getLatitude());
		entity.setProperty("longitude", facility.getLongitude());
		entity.setProperty("riskCategory1", facility.getRiskCategory1());
		entity.setProperty("riskCategory2", facility.getRiskCategory2());
		entity.setProperty("riskCategory3", facility.getRiskCategory3());
		entity.setProperty("recoveryTime1", facility.getRecoveryTime1());
		entity.setProperty("recoveryTime2", facility.getRecoveryTime2());
		entity.setProperty("recoveryTime3", facility.getRecoveryTime3());

		return entity;
	}

}
