package com.scirisk.riskanalyzer.backend.service;

import org.jdom2.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class FrequencyDistributionEndpoint {

	@Autowired
	FrequencyDistributionService frequencyDistributionService;
	RequestMarshaller requestMarshaller = new RequestMarshaller();
	ResponseMarshaller responseMarshaller = new ResponseMarshaller();

	@PayloadRoot(namespace = "http://scirisk.com/xml/ns/risk-analyzer", localPart = "CalculateRequest")
	public @ResponsePayload
	Element FrequencyDistribution(@RequestPayload Element requestElm)
			throws Exception {
		System.out.println("FQE CALCULATE REQUEST RECEIVED..");
		CalculateRequest calculateRequest = requestMarshaller.unmarshall(requestElm);
		CalculateResponse calculateResponse = frequencyDistributionService
				.calculate(calculateRequest);
		Element responseElm = responseMarshaller.marshall(calculateResponse);
		System.out.println("FQE CALCULATE REQUEST COMPLETE");
		return responseElm;
	}

}
