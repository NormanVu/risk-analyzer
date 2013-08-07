package com.danielpacak.riskanalyzer.backend.web.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.danielpacak.riskanalyzer.backend.service.api.CalculateRequest;
import com.danielpacak.riskanalyzer.backend.service.api.CalculateResponse;
import com.danielpacak.riskanalyzer.backend.service.api.FrequencyDistributionService;

@RunWith(MockitoJUnitRunner.class)
public class FrequencyDistributionEndpointTest {

	@Mock
	FrequencyDistributionService frequencyDistributionService;
	FrequencyDistributionController controller;

	@Before
	public void beforeTest() throws Exception {
		controller = new FrequencyDistributionController(frequencyDistributionService);
	}

	@Test
	public void tesFrequencyDistribution() throws Exception {
		CalculateRequest request = new CalculateRequest();
		CalculateResponse response = new CalculateResponse();

		when(frequencyDistributionService.calculate(request)).thenReturn(response);

		assertEquals(response, controller.frequencyDistribution(request));
	}

}
