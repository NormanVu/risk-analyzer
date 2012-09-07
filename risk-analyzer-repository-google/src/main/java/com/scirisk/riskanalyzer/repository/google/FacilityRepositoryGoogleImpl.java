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
import com.scirisk.google.persistence.EntityManager;
import com.scirisk.google.persistence.EntityManagerImpl;
import com.scirisk.riskanalyzer.domain.Facility;
import com.scirisk.riskanalyzer.domain.Facility.Kind;
import com.scirisk.riskanalyzer.domain.Facility.Type;
import com.scirisk.riskanalyzer.repository.FacilityRepository;

public class FacilityRepositoryGoogleImpl implements FacilityRepository {

	public static final String FACILITY_ENTITY = "facilityEntity";

	private DatastoreService datastoreService;
	private EntityManager em;

	public FacilityRepositoryGoogleImpl(DatastoreService datastoreService) {
		this.datastoreService = datastoreService;
		this.em = new EntityManagerImpl(datastoreService);
	}

	@Override
	public Facility save(Facility facility) {
		return em.save(facility);
	}

	@Override
	public void delete(String facilityId) {
		em.delete(new com.scirisk.google.persistence.Key<Facility>(
				Facility.class, Long.valueOf(facilityId)));
		/*
		 * Key nodeKey = KeyFactory.createKey(FACILITY_ENTITY,
		 * Long.valueOf(facilityId)); datastoreService.beginTransaction();
		 * datastoreService.delete(nodeKey);
		 * datastoreService.getCurrentTransaction().commit();
		 */
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
		Key facilityKey = KeyFactory.createKey(FACILITY_ENTITY,
				Long.valueOf(facilityId));
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
		facility.setKind(Kind.valueOf((String) facilityEntity
				.getProperty("kind")));
		facility.setDescription((String) facilityEntity
				.getProperty("description"));
		facility.setAddress((String) facilityEntity.getProperty("address"));
		facility.setLatitude((Double) facilityEntity.getProperty("latitude"));
		facility.setLongitude((Double) facilityEntity.getProperty("longitude"));
		facility.setRiskCategory1((Double) facilityEntity
				.getProperty("riskCategory1"));
		facility.setRiskCategory2((Double) facilityEntity
				.getProperty("riskCategory2"));
		facility.setRiskCategory3((Double) facilityEntity
				.getProperty("riskCategory3"));
		facility.setRecoveryTime1((Double) facilityEntity
				.getProperty("recoveryTime1"));
		facility.setRecoveryTime2((Double) facilityEntity
				.getProperty("recoveryTime2"));
		facility.setRecoveryTime3((Double) facilityEntity
				.getProperty("recoveryTime3"));
		facility.setType(Type.valueOf((String) facilityEntity
				.getProperty("type")));

		return facility;
	}

	boolean isBlank(String string) {
		return "".equals(string);
	}

}
