package com.danielpacak.riskanalyzer.backend.service.proxy;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.ws.client.core.WebServiceOperations;

import com.danielpacak.riskanalyzer.backend.service.api.CalculateRequest;

/**
 * Tests for {@link FrequencyDistributionServiceSoapProxy}.
 */
@RunWith(MockitoJUnitRunner.class)
public class FrequencyDistributionServiceSoapProxyTest {

	@Mock
	RequestMarshaller requestMarshaller;

	@Mock
	ResponseMarshaller responseMarshaller;

	@Mock
	WebServiceOperations webServiceOperations;

	@Test
	public void testCalculate() throws Exception {
		FrequencyDistributionServiceSoapProxy proxy = new FrequencyDistributionServiceSoapProxy(webServiceOperations,
				requestMarshaller, responseMarshaller);
		CalculateRequest request = new CalculateRequest();

		proxy.calculate(request);
		Mockito.verify(requestMarshaller).marshall(request);
	}

}
