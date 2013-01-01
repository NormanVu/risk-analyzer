package com.danielpacak.riskanalyzer.frontend.repository.google;

import java.util.List;

import com.danielpacak.riskanalyzer.domain.Facility;
import com.danielpacak.riskanalyzer.frontend.repository.FacilityRepository;
import com.google.appengine.api.datastore.DatastoreService;
import com.scirisk.google.persistence.EntityManager;
import com.scirisk.google.persistence.EntityManagerImpl;

public class FacilityRepositoryGoogleImpl implements FacilityRepository {

	private DatastoreService datastoreService;
	private EntityManager em;
	private GoogleDatastoreTemplate datastoreTemplate;

	public FacilityRepositoryGoogleImpl(DatastoreService datastoreService) {
		this.datastoreService = datastoreService;
		this.em = new EntityManagerImpl(datastoreService);
		this.datastoreTemplate = new GoogleDatastoreTemplate(datastoreService);
	}

	@Override
	public Facility save(Facility facility) {
		return em.save(facility);
	}

	@Override
	public void delete(String facilityId) {
		datastoreTemplate.delete(Facility.class, facilityId);
	}

	@Override
	public List<Facility> findAll() {
		return datastoreTemplate.findAll(Facility.class, new FacilityReadConverter());
	}

	@Override
	public Facility findOne(String facilityId) {
		return datastoreTemplate.findById(facilityId, Facility.class, new FacilityReadConverter());
	}

}
