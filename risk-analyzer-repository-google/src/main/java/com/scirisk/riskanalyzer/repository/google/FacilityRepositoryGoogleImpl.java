package com.scirisk.riskanalyzer.repository.google;

import java.util.ArrayList;
import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.scirisk.riskanalyzer.domain.Facility;
import com.scirisk.riskanalyzer.domain.Facility.Kind;
import com.scirisk.riskanalyzer.domain.Facility.Type;
import com.scirisk.riskanalyzer.repository.FacilityRepository;

public class FacilityRepositoryGoogleImpl implements FacilityRepository {

	static final String FACILITY_ENTITY = "facilityEntity";

	private DatastoreService datastoreService;

	public FacilityRepositoryGoogleImpl(DatastoreService datastoreService) {
		this.datastoreService = datastoreService;
	}

	public Facility save(Facility facility) {
		Entity facilityEntity = null;
		if (!isBlank(facility.getId())) {
			Key nodeKey = KeyFactory.createKey(FACILITY_ENTITY,
					Long.valueOf(facility.getId()));
			try {
				facilityEntity = datastoreService.get(nodeKey);
			} catch (EntityNotFoundException e) {
				throw new IllegalArgumentException(
						"Cannot find network node entity [" + facility.getId()
								+ "].");
			}
		} else {
			facilityEntity = new Entity(FACILITY_ENTITY);
		}
		facilityEntity.setProperty("name", facility.getName());
		facilityEntity.setProperty("kind", facility.getKind().toString());
		facilityEntity.setProperty("description", facility.getDescription());
		facilityEntity.setProperty("address", facility.getAddress());
		facilityEntity.setProperty("latitude", facility.getLatitude());
		facilityEntity.setProperty("longitude", facility.getLongitude());
		facilityEntity
				.setProperty("riskCategory1", facility.getRiskCategory1());
		facilityEntity
				.setProperty("riskCategory2", facility.getRiskCategory2());
		facilityEntity
				.setProperty("riskCategory3", facility.getRiskCategory3());
		facilityEntity
				.setProperty("recoveryTime1", facility.getRecoveryTime1());
		facilityEntity
				.setProperty("recoveryTime2", facility.getRecoveryTime2());
		facilityEntity
				.setProperty("recoveryTime3", facility.getRecoveryTime3());
		facilityEntity.setProperty("type", facility.getType().toString());

		datastoreService.beginTransaction();
		Key generatedKey = datastoreService.put(facilityEntity);
		datastoreService.getCurrentTransaction().commit();
		facility.setId(String.valueOf(generatedKey.getId()));
		return facility;
	}

	public void delete(String facilityId) {
		Key nodeKey = KeyFactory.createKey(FACILITY_ENTITY, Long.valueOf(facilityId));
		datastoreService.beginTransaction();
		datastoreService.delete(nodeKey);
		datastoreService.getCurrentTransaction().commit();
	}

	public List<Facility> findAll() {
		Query q = new Query(FACILITY_ENTITY);
		PreparedQuery pq = datastoreService.prepare(q);
		List<Facility> nodes = new ArrayList<Facility>();
		for (Entity nodeEntity : pq.asIterable()) {
			Facility node = map(nodeEntity);
			nodes.add(node);
		}
		return nodes;
	}

	public Facility findOne(String facilityId) {
		Key facilityKey = KeyFactory.createKey(FACILITY_ENTITY, Long.valueOf(facilityId));
		try {
			Entity facilityEntity = datastoreService.get(facilityKey);
			return map(facilityEntity);
		} catch (EntityNotFoundException e) {
			throw new IllegalArgumentException(
					"Cannot find network node entity [" + facilityId + "].");
		}
	}

	Facility map(Entity facilityEntity) {
		Facility facility = new Facility();
		facility.setId(String.valueOf(facilityEntity.getKey().getId()));
		facility.setName((String) facilityEntity.getProperty("name"));
		facility.setKind(Kind.valueOf((String) facilityEntity.getProperty("kind")));
		facility.setDescription((String) facilityEntity.getProperty("description"));
		facility.setAddress((String) facilityEntity.getProperty("address"));
		facility.setLatitude((Double) facilityEntity.getProperty("latitude"));
		facility.setLongitude((Double) facilityEntity.getProperty("longitude"));
		facility.setRiskCategory1((Double) facilityEntity.getProperty("riskCategory1"));
		facility.setRiskCategory2((Double) facilityEntity.getProperty("riskCategory2"));
		facility.setRiskCategory3((Double) facilityEntity.getProperty("riskCategory3"));
		facility.setRecoveryTime1((Double) facilityEntity.getProperty("recoveryTime1"));
		facility.setRecoveryTime2((Double) facilityEntity.getProperty("recoveryTime2"));
		facility.setRecoveryTime3((Double) facilityEntity.getProperty("recoveryTime3"));
		facility.setType(Type.valueOf((String) facilityEntity.getProperty("type")));

		return facility;
	}

	boolean isBlank(String string) {
		return "".equals(string);
	}

}
