package com.danielpacak.riskanalyzer.backend.service.proxy;

import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.client.RestOperations;

import com.danielpacak.riskanalyzer.backend.service.api.CalculateRequest;
import com.danielpacak.riskanalyzer.backend.service.api.CalculateResponse;

/**
 * Tests for {@link FrequencyDistributionServiceSoapProxy}.
 */
@RunWith(MockitoJUnitRunner.class)
public class FrequencyDistributionServiceSoapProxyTest {

	@Mock
	private RestOperations restOperations;

	@Test
	public void testCalculate() throws Exception {
		FrequencyDistributionServiceSoapProxy proxy = new FrequencyDistributionServiceSoapProxy(restOperations, "http",
				"localhost", 8080, "/api");
		CalculateRequest request = new CalculateRequest();
		proxy.calculate(request);
		verify(restOperations).postForObject("http://localhost:8080/api/frequency-distribution", request,
				CalculateResponse.class);
	}

}
