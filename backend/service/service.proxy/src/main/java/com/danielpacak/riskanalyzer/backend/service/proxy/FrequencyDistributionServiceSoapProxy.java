package com.danielpacak.riskanalyzer.backend.service.proxy;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.springframework.ws.client.core.WebServiceOperations;

import com.danielpacak.riskanalyzer.backend.service.api.CalculateRequest;
import com.danielpacak.riskanalyzer.backend.service.api.CalculateResponse;
import com.danielpacak.riskanalyzer.backend.service.api.FrequencyDistributionService;

public class FrequencyDistributionServiceSoapProxy implements FrequencyDistributionService {

	private RequestMarshaller requestMarshaller = new JDom2RequestMarshaller();
	private ResponseMarshaller responseMarshaller = new JDom2ResponseMarshaller();

	private WebServiceOperations webServiceOperations;

	public FrequencyDistributionServiceSoapProxy(WebServiceOperations webServiceOperations,
			RequestMarshaller requestMarshaller, ResponseMarshaller responseMarshaller) {
		this.webServiceOperations = webServiceOperations;
		this.requestMarshaller = requestMarshaller;
		this.responseMarshaller = responseMarshaller;
	}

	@Override
	public CalculateResponse calculate(CalculateRequest request) throws Exception {
		StreamSource source = requestMarshaller.marshall(request);

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		StreamResult result = new StreamResult(out);

		webServiceOperations.sendSourceAndReceiveToResult(source, result);

		return responseMarshaller.unmarshall(new StreamSource(new ByteArrayInputStream(out.toByteArray())));
	}

}
