package com.danielpacak.riskanalyzer.frontend.repository.mongodb;

import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.danielpacak.riskanalyzer.domain.Facility;

public class FacilityRepositoryMongoDbImplTest {

	MongoTemplate mockMongoTemplate;
	FacilityRepositoryMongoDbImpl facilityRepository;

	@Before
	public void beforeEachTest() throws Exception {
		mockMongoTemplate = Mockito.mock(MongoTemplate.class);
		facilityRepository = new FacilityRepositoryMongoDbImpl(mockMongoTemplate);
	}

	@Test
	public void testSave() throws Exception {
		Facility facility = new Facility();
		facilityRepository.save(facility);
		verify(mockMongoTemplate).save(eq(facility), isA(FacilityWriteConverter.class));
	}

	@Test
	public void testFindOne() throws Exception {
		String facilityId = "666";
		facilityRepository.findOne(facilityId);
		verify(mockMongoTemplate).findById(eq(facilityId), eq(Facility.class), isA(FacilityReadConverter.class));
	}

	@Test
	public void testFindAll() throws Exception {
		facilityRepository.findAll();
		verify(mockMongoTemplate).findAll(eq(Facility.class), isA(FacilityReadConverter.class));
	}

	@Test
	public void testDelete() throws Exception {
		String facilityId = "666";

		facilityRepository.delete(facilityId);

		verify(mockMongoTemplate).delete(Facility.class, facilityId);
	}

}
