package com.danielpacak.riskanalyzer.frontend.repository.mongodb;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.danielpacak.riskanalyzer.frontend.repository.mongodb.Collection;
import com.danielpacak.riskanalyzer.frontend.repository.mongodb.FacilityRepositoryMongoDbImpl;
import com.danielpacak.riskanalyzer.frontend.repository.mongodb.MongoTemplate;

public class FacilityRepositoryMongoDbImplTest {

	MongoTemplate mockMongoTemplate;
	FacilityRepositoryMongoDbImpl facilityRepository;

	@Before
	public void beforeEachTest() throws Exception {
		mockMongoTemplate = Mockito.mock(MongoTemplate.class);
		facilityRepository = new FacilityRepositoryMongoDbImpl(mockMongoTemplate);
	}

	@Test
	public void testDelete() throws Exception {
		String facilityId = "666";

		facilityRepository.delete(facilityId);

		Mockito.verify(mockMongoTemplate).delete(Collection.facilities.name(), facilityId);
	}

	@Test
	public void testFindAll() throws Exception {
		facilityRepository.findAll();
	}

	@Test
	public void testSave() throws Exception {
	}

}
