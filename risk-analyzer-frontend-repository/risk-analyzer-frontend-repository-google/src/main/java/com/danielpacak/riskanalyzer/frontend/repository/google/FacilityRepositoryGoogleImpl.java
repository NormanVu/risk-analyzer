package com.danielpacak.riskanalyzer.frontend.repository.google;

import java.util.ArrayList;
import java.util.List;

import com.danielpacak.riskanalyzer.domain.Facility;
import com.danielpacak.riskanalyzer.frontend.repository.FacilityRepository;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.scirisk.google.persistence.DynamicBean;
import com.scirisk.google.persistence.EntityManager;
import com.scirisk.google.persistence.EntityManagerImpl;

public class FacilityRepositoryGoogleImpl implements FacilityRepository {

	//public static final String FACILITY_ENTITY = "facilityEntity";

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
	}

	@Override
	public List<Facility> findAll() {
		Query q = new Query(Facility.class.getName());
		PreparedQuery pq = datastoreService.prepare(q);
		List<Facility> nodes = new ArrayList<Facility>();
		for (Entity nodeEntity : pq.asIterable()) {
			DynamicBean<Facility> dynamicBean = new DynamicBean<Facility>(
					new Facility());
			dynamicBean.setProperties(nodeEntity.getProperties());
			dynamicBean.setProperty("id", String.valueOf(nodeEntity.getKey().getId()));

			nodes.add(dynamicBean.getBean());
		}
		return nodes;
	}

	public Facility findOne(String facilityId) {
		return em.find(new com.scirisk.google.persistence.Key<Facility>(
				Facility.class, Long.valueOf(facilityId)));
	}

}
