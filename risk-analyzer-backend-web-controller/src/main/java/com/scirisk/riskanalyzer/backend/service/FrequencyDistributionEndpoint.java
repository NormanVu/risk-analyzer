package com.scirisk.riskanalyzer.backend.service;

import javax.xml.transform.stream.StreamSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class FrequencyDistributionEndpoint {

	@Autowired
	FrequencyDistributionService frequencyDistributionService;
	RequestMarshallerJDomImpl requestMarshaller = new RequestMarshallerJDomImpl();
	ResponseMarshallerJDomIMpl responseMarshaller = new ResponseMarshallerJDomIMpl();

	@PayloadRoot(namespace = "http://scirisk.com/xml/ns/risk-analyzer", localPart = "CalculateRequest")
	public @ResponsePayload
	StreamSource FrequencyDistribution(@RequestPayload StreamSource request)
			throws Exception {
		System.out.println("FREQUENCY DISTRIBUTION REQUEST RECEIVED..");
		CalculateRequest calculateRequest = requestMarshaller
				.unmarshall(request);

		CalculateResponse calculateResponse = frequencyDistributionService
				.calculate(calculateRequest);
		StreamSource response = responseMarshaller.marshall(calculateResponse);
		System.out.println("FREQUENCY DISTRIBUTION REQUEST PROCESSED");
		return response;
	}

}
