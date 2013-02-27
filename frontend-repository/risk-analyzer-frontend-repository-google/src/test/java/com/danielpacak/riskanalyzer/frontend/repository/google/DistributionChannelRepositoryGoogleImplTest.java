package com.danielpacak.riskanalyzer.frontend.repository.google;

import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.danielpacak.riskanalyzer.domain.DistributionChannel;

public class DistributionChannelRepositoryGoogleImplTest {

	GoogleDatastoreTemplate mockDatastoreTemplate;
	DistributionChannelRepositoryGoogleImpl repository;

	@Before
	public void beforeEachTest() throws Exception {
		mockDatastoreTemplate = Mockito.mock(GoogleDatastoreTemplate.class);
		repository = new DistributionChannelRepositoryGoogleImpl(mockDatastoreTemplate);
	}

	@Test
	public void testSave() throws Exception {
		DistributionChannel channel = new DistributionChannel();
		String sourceId = "666";
		String targetId = "999";
		repository.save(channel, sourceId, targetId);
		verify(mockDatastoreTemplate).put(eq(channel), isA(DistributionChannelWriteConverter.class));
	}

	@Test
	public void testDelete() throws Exception {
		String channelId = "666";
		repository.delete(channelId);
		verify(mockDatastoreTemplate).delete(DistributionChannel.class, channelId);
	}

	@Test
	public void testFindOne() throws Exception {
		final String channelId = "666";
		repository.findOne(channelId);
		verify(mockDatastoreTemplate).findById(eq(channelId), eq(DistributionChannel.class),
				isA(DistributionChannelReadConverter.class));
	}

	@Test
	public void testFindAll() throws Exception {
		repository.findAll();
		verify(mockDatastoreTemplate).findAll(eq(DistributionChannel.class),
				isA(DistributionChannelReadConverter.class));
	}

}
