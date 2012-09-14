package com.scirisk.riskanalyzer.repository.neo4j;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.test.TestGraphDatabaseFactory;

import com.scirisk.riskanalyzer.domain.DistributionChannel;
import com.scirisk.riskanalyzer.domain.Facility;
import com.scirisk.riskanalyzer.domain.Facility.Kind;
import com.scirisk.riskanalyzer.domain.Facility.Type;
import com.scirisk.riskanalyzer.repository.DistributionChannelRepository;
import com.scirisk.riskanalyzer.repository.FacilityRepository;

public class DistributionChannelRepositoryNeo4jImplTest {

	GraphDatabaseService databaseService;
	FacilityRepository facilityRepository;
	DistributionChannelRepository channelRepository;

	@Before
	public void beforeTest() throws Exception {
		databaseService = new TestGraphDatabaseFactory()
				.newImpermanentDatabaseBuilder().newGraphDatabase();
		facilityRepository = new FacilityRepositoryNeo4jImpl(databaseService);
		channelRepository = new DistributionChannelRepositoryNeo4jImpl(
				databaseService);
	}

	@Test
	public void testSaveFindOneAndDelete() throws Exception {
		Facility source = newFacility("Source");
		Facility target = newFacility("Target");
		facilityRepository.save(source);
		facilityRepository.save(target);

		DistributionChannel channel = new DistributionChannel();
		channel.setPurchasingVolume(0.5);
		channelRepository.save(channel, source.getId(), target.getId());

		DistributionChannel foundChannel = channelRepository.findOne(channel
				.getId());
		assertEquals(channel.getId(), foundChannel.getId());
		assertEquals(channel.getPurchasingVolume(),
				foundChannel.getPurchasingVolume());
		assertEquals(source.getId(), foundChannel.getSource().getId());
		assertEquals(target.getId(), foundChannel.getTarget().getId());
		
		channelRepository.delete(foundChannel.getId());
	}
	
	@Test
	public void testUpdate() throws Exception {
		Facility source = newFacility("Source");
		Facility target = newFacility("Target");
		facilityRepository.save(source);
		facilityRepository.save(target);
		
		DistributionChannel channel = new DistributionChannel();
		channel.setPurchasingVolume(0.5);

		String channelId = channelRepository.save(channel, source.getId(), target.getId()).getId();
		
		DistributionChannel foundChannel = channelRepository.findOne(channelId);
		
		foundChannel.setPurchasingVolume(1.0);

		channelRepository.save(foundChannel, source.getId(), target.getId());

		DistributionChannel updatedChannel = channelRepository.findOne(channelId);
		assertEquals(foundChannel.getPurchasingVolume(), updatedChannel.getPurchasingVolume());
	}

	@After
	public void afterTest() throws Exception {
		databaseService.shutdown();
	}

	Facility newFacility(String name) {
		Facility facility = new Facility();
		facility.setName(name);
		facility.setKind(Kind.company);
		facility.setDescription("No description");
		facility.setAddress(name);
		facility.setLatitude(0.0);
		facility.setLongitude(0.0);
		facility.setRiskCategory1(1.0);
		facility.setRiskCategory2(2.0);
		facility.setRiskCategory3(3.0);
		facility.setRecoveryTime1(10.0);
		facility.setRecoveryTime2(20.0);
		facility.setRecoveryTime3(30.0);
		facility.setType(Type.independent);
		return facility;
	}

}
