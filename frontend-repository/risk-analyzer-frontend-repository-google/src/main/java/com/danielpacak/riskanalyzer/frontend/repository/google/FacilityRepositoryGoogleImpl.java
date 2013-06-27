package com.danielpacak.riskanalyzer.frontend.repository.google;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;

import com.danielpacak.riskanalyzer.domain.Facility;
import com.danielpacak.riskanalyzer.frontend.repository.FacilityRepository;

public class FacilityRepositoryGoogleImpl implements FacilityRepository {

	private GoogleDatastoreTemplate datastoreTemplate;

	public FacilityRepositoryGoogleImpl(GoogleDatastoreTemplate datastoreTemplate) {
		this.datastoreTemplate = datastoreTemplate;
	}

	@Override
	public Facility save(Facility facility) {
		return datastoreTemplate.put(facility, new FacilityWriteConverter());
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
	@Cacheable("facility")
	public Facility findOne(String facilityId) {
		return datastoreTemplate.findById(facilityId, Facility.class, new FacilityReadConverter());
	}

}
