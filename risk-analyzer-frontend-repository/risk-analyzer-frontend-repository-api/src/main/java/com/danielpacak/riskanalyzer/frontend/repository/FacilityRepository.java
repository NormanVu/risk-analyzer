package com.danielpacak.riskanalyzer.frontend.repository;

import java.util.List;

import com.danielpacak.riskanalyzer.domain.Facility;

public interface FacilityRepository {

	Facility save(Facility facility);

	Facility findOne(String facilityId);

	void delete(String facilityId);

	// void deleteAll(); FIXME IMPLEMENT THIS METHOD

	List<Facility> findAll();

}
