package com.danielpacak.riskanalyzer.frontend.repository.mongodb;

import java.util.List;

import com.danielpacak.riskanalyzer.domain.Facility;
import com.danielpacak.riskanalyzer.frontend.repository.api.FacilityRepository;

public class FacilityRepositoryMongoDbImpl implements FacilityRepository {

	private MongoTemplate mongoTemplate;

	public FacilityRepositoryMongoDbImpl(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	public Facility save(Facility facility) {
		return mongoTemplate.save(facility, new FacilityWriteConverter());
	}

	public Facility findOne(String facilityId) {
		return mongoTemplate.findById(facilityId, Facility.class, new FacilityReadConverter());
	}

	public List<Facility> findAll() {
		return mongoTemplate.findAll(Facility.class, new FacilityReadConverter());
	}

	public void delete(String facilityId) {
		mongoTemplate.delete(Facility.class, facilityId);
	}

}
