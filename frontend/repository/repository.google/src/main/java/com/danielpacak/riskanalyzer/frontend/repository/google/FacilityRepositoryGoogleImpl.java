package com.danielpacak.riskanalyzer.frontend.repository.google;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import com.danielpacak.riskanalyzer.domain.Facility;
import com.danielpacak.riskanalyzer.frontend.repository.api.FacilityRepository;

public class FacilityRepositoryGoogleImpl implements FacilityRepository {

	private GoogleDatastoreTemplate datastoreTemplate;

	public FacilityRepositoryGoogleImpl(GoogleDatastoreTemplate datastoreTemplate) {
		this.datastoreTemplate = datastoreTemplate;
	}

	@CacheEvict(value = "facility", key = "#facility.id", condition = "#facility.id != null")
	public Facility save(Facility facility) {
		return datastoreTemplate.put(facility, new FacilityWriteConverter());
	}

	@CacheEvict(value = "facility")
	public void delete(String facilityId) {
		datastoreTemplate.delete(Facility.class, facilityId);
	}

	@Cacheable(value = "facility")
	public Facility findOne(String facilityId) {
		return datastoreTemplate.findById(facilityId, Facility.class, new FacilityReadConverter());
	}

	public List<Facility> findAll() {
		return datastoreTemplate.findAll(Facility.class, new FacilityReadConverter());
	}

}
