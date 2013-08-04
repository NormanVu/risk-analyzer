package com.danielpacak.riskanalyzer.frontend.repository.mongodb;

import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.danielpacak.riskanalyzer.domain.DistributionChannel;

public class DistributionChannelRepositoryMongoDbImplTest {

	MongoTemplate mockMongoTemplate;
	DistributionChannelRepositoryMongoDbImpl repository;

	@Before
	public void beforeEachTest() throws Exception {
		mockMongoTemplate = Mockito.mock(MongoTemplate.class);
		repository = new DistributionChannelRepositoryMongoDbImpl(mockMongoTemplate);
	}

	@Test
	public void testSave() throws Exception {
		DistributionChannel distributionChannel = new DistributionChannel();
		String sourceId = "666";
		String targetId = "999";
		repository.save(distributionChannel, sourceId, targetId);
		verify(mockMongoTemplate).save(eq(distributionChannel), isA(DistributionChannelWriteConverter.class));
	}

	@Test
	public void testFindOne() throws Exception {
		String channelId = "666";

		repository.findOne(channelId);
		verify(mockMongoTemplate).findById(eq(channelId), eq(DistributionChannel.class),
				isA(DistributionChannelReadConverter.class));
	}

	@Test
	public void testFindAll() throws Exception {
		repository.findAll();
		verify(mockMongoTemplate).findAll(eq(DistributionChannel.class), isA(DistributionChannelReadConverter.class));
	}

	@Test
	public void testDelete() throws Exception {
		String channelId = "666";
		repository.delete(channelId);
		verify(mockMongoTemplate).delete(DistributionChannel.class, channelId);
	}

}
