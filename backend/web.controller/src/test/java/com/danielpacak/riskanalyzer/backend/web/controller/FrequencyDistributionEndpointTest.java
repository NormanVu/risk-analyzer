package com.danielpacak.riskanalyzer.backend.web.controller;

import static org.mockito.Mockito.mock;

import javax.xml.transform.stream.StreamSource;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import com.danielpacak.riskanalyzer.backend.service.proxy.CalculateRequest;
import com.danielpacak.riskanalyzer.backend.service.proxy.CalculateResponse;
import com.danielpacak.riskanalyzer.backend.service.proxy.FrequencyDistributionService;
import com.danielpacak.riskanalyzer.backend.service.proxy.RequestMarshaller;
import com.danielpacak.riskanalyzer.backend.service.proxy.ResponseMarshaller;

public class FrequencyDistributionEndpointTest {

	@Test
	public void tesMe() throws Exception {
		FrequencyDistributionService mockFrequencyDistributionService = mock(FrequencyDistributionService.class);
		RequestMarshaller mockRequestMarshaller = mock(RequestMarshaller.class);
		ResponseMarshaller mockResponseMarshaller = mock(ResponseMarshaller.class);

		FrequencyDistributionEndpoint endpoint = new FrequencyDistributionEndpoint();

		StreamSource requestSource = mock(StreamSource.class);
		StreamSource responseSource = mock(StreamSource.class);
		CalculateRequest request = new CalculateRequest();
		CalculateResponse response = new CalculateResponse();

		Mockito.when(mockFrequencyDistributionService.calculate(request)).thenReturn(response);
		Mockito.when(mockRequestMarshaller.unmarshall(requestSource)).thenReturn(request);
		Mockito.when(mockResponseMarshaller.marshall(response)).thenReturn(responseSource);

		endpoint.frequencyDistributionService = mockFrequencyDistributionService;
		endpoint.requestMarshaller = mockRequestMarshaller;
		endpoint.responseMarshaller = mockResponseMarshaller;

		Assert.assertEquals(responseSource, endpoint.frequencyDistribution(requestSource));

		Mockito.verify(mockFrequencyDistributionService).calculate(request);

	}

}
