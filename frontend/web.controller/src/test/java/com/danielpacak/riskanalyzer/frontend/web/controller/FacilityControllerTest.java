package com.danielpacak.riskanalyzer.frontend.web.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.danielpacak.riskanalyzer.domain.Facility;
import com.danielpacak.riskanalyzer.frontend.repository.api.FacilityRepository;

/**
 * Tests for {@link FacilityController}.
 */
@RunWith(MockitoJUnitRunner.class)
public class FacilityControllerTest {

	@Mock
	FacilityRepository repository;

	FacilityController controller;

	@Before
	public void beforeTest() {
		controller = new FacilityController(repository);
	}

	@Test
	public void testSave() throws Exception {
		Facility facility = new Facility();
		ResponseEntity<String> responseEntity = controller.save(facility);

		verify(repository).save(Mockito.eq(facility));
		assertEquals(HttpStatus.ACCEPTED, responseEntity.getStatusCode());
	}

	@Test
	public void testRead() throws Exception {
		String facilityId = "13";
		Facility facility = new Facility();
		when(repository.findOne(facilityId)).thenReturn(facility);
		Facility foundFacility = controller.read(facilityId);

		verify(repository).findOne(facilityId);
		assertEquals(facility, foundFacility);
	}

	@Test
	public void testDelete() throws Exception {
		String facilityId = "13";
		ResponseEntity<String> responseEntity = controller.delete(facilityId);
		verify(repository).delete(facilityId);
		assertEquals(HttpStatus.ACCEPTED, responseEntity.getStatusCode());
	}

	@Test
	public void testFindAll() throws Exception {
		List<Facility> facilities = Arrays.asList(new Facility());
		when(repository.findAll()).thenReturn(facilities);
		List<Facility> foundFacilities = controller.findAll();
		assertEquals(facilities, foundFacilities);
	}

}
