package com.scirisk.riskanalyzer.repository.neo4j;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.test.TestGraphDatabaseFactory;

import com.scirisk.riskanalyzer.domain.Facility;
import com.scirisk.riskanalyzer.domain.Facility.Kind;
import com.scirisk.riskanalyzer.domain.Facility.Type;
import com.scirisk.riskanalyzer.repository.FacilityRepository;

public class FacilityRepositoryNeo4jImplTest {

	GraphDatabaseService databaseService;
	FacilityRepository facilityRepository;

	@Before
	public void beforeTest() throws Exception {
		databaseService = new TestGraphDatabaseFactory()
				.newImpermanentDatabaseBuilder().newGraphDatabase();
		facilityRepository = new FacilityRepositoryNeo4jImpl(databaseService);
	}

	@Test
	public void testSaveFindOneAndDelete() throws Exception {
		Facility facility = new Facility();
		facility.setName("Antibes");
		facility.setKind(Kind.supplier);
		facility.setDescription("No description");
		facility.setAddress("Antibes, 28 Rue Lacan");
		facility.setLongitude(100.0);
		facility.setLatitude(200.0);
		facility.setRiskCategory1(1.0);
		facility.setRiskCategory2(2.0);
		facility.setRiskCategory3(3.0);
		facility.setRecoveryTime1(10.0);
		facility.setRecoveryTime2(20.0);
		facility.setRecoveryTime3(30.0);
		facility.setType(Type.correlated);

		facilityRepository.save(facility);
		Facility foundFacility = facilityRepository.findOne(facility.getId());

		assertEquals(facility.getId(), foundFacility.getId());
		assertEquals(facility.getName(), foundFacility.getName());
		assertEquals(facility.getKind(), foundFacility.getKind());
		assertEquals(facility.getDescription(), foundFacility.getDescription());
		assertEquals(facility.getAddress(), foundFacility.getAddress());
		assertEquals(facility.getLongitude(), foundFacility.getLongitude());
		assertEquals(facility.getLatitude(), foundFacility.getLatitude());
		assertEquals(facility.getType(), foundFacility.getType());

		facilityRepository.delete(foundFacility.getId());
		List<Facility> allFacilities = facilityRepository.findAll();
		assertTrue(allFacilities.isEmpty());
	}

	@Test
	public void testUpdate() throws Exception {
		Facility facility = new Facility();
		facility.setName("Antibes");
		facility.setKind(Kind.supplier);
		facility.setDescription("No description");
		facility.setAddress("Antibes, 28 Rue Lacan");
		facility.setLongitude(100.0);
		facility.setLatitude(200.0);
		facility.setRiskCategory1(1.0);
		facility.setRiskCategory2(2.0);
		facility.setRiskCategory3(3.0);
		facility.setRecoveryTime1(10.0);
		facility.setRecoveryTime2(20.0);
		facility.setRecoveryTime3(30.0);
		facility.setType(Type.correlated);

		String facilityId = facilityRepository.save(facility).getId();

		Facility foundFacility = facilityRepository.findOne(facilityId);

		foundFacility.setName("Antibes, 28 Rue Lacan");
		foundFacility.setDescription("Fixed description");

		facilityRepository.save(foundFacility);

		Facility updatedFacility = facilityRepository.findOne(facilityId);
		assertEquals(foundFacility.getName(), updatedFacility.getName());
		assertEquals(foundFacility.getDescription(),
				updatedFacility.getDescription());
	}

	@After
	public void afterTest() throws Exception {
		databaseService.shutdown();
	}

}
