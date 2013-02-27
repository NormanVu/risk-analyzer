package com.danielpacak.riskanalyzer.frontend.repository.google;

import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;

import com.danielpacak.riskanalyzer.domain.Facility;

// TODO Implement tests
//@RunWith(PowerMockRunner.class)
//@PrepareForTest({ KeyFactory.class, Key.class })
public class FacilityRepositoryGoogleImplTest {

	GoogleDatastoreTemplate mockDatastoreTemplate;
	FacilityRepositoryGoogleImpl repository;

	@Before
	public void beforeEachTest() throws Exception {
		mockDatastoreTemplate = mock(GoogleDatastoreTemplate.class);
		repository = new FacilityRepositoryGoogleImpl(mockDatastoreTemplate);
	}

	@Test
	public void testSave() throws Exception {
		Facility facility = new Facility();
		repository.save(facility);
		verify(mockDatastoreTemplate).put(eq(facility), isA(FacilityWriteConverter.class));
	}

	@Test
	public void testDelete() throws Exception {
		String facilityId = "666";
		repository.delete(facilityId);
		verify(mockDatastoreTemplate).delete(Facility.class, facilityId);
	}

	@Test
	public void testFindOne() throws Exception {
		String facilityId = "666";
		repository.findOne(facilityId);
		verify(mockDatastoreTemplate).findById(eq(facilityId), eq(Facility.class), isA(FacilityReadConverter.class));
	}

	@Test
	public void testFindAll() throws Exception {
		repository.findAll();
		verify(mockDatastoreTemplate).findAll(eq(Facility.class), isA(FacilityReadConverter.class));
	}

}
