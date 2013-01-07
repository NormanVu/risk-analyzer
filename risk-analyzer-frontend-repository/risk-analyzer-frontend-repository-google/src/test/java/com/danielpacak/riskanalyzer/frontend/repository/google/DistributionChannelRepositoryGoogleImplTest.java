package com.danielpacak.riskanalyzer.frontend.repository.google;

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
	public void testDelete() throws Exception {
		String channelId = "666";
		repository.delete(channelId);
		Mockito.verify(mockDatastoreTemplate).delete(DistributionChannel.class, channelId);
	}

}
