package com.scirisk.riskanalyzer.backend.proxy;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.springframework.ws.client.core.WebServiceTemplate;

import com.danielpacak.riskanalyzer.backend.service.CalculateRequest;
import com.danielpacak.riskanalyzer.backend.service.CalculateResponse;
import com.danielpacak.riskanalyzer.backend.service.FrequencyDistributionService;
import com.danielpacak.riskanalyzer.backend.service.RequestMarshaller;
import com.danielpacak.riskanalyzer.backend.service.RequestMarshallerJDomImpl;
import com.danielpacak.riskanalyzer.backend.service.ResponseMarshaller;
import com.danielpacak.riskanalyzer.backend.service.ResponseMarshallerJDomIMpl;

public class FrequencyDistributionServiceSoapProxy implements
		FrequencyDistributionService {

	private String endpointUrl;
	private RequestMarshaller requestMarshaller = new RequestMarshallerJDomImpl();
	private ResponseMarshaller responseMarshaller = new ResponseMarshallerJDomIMpl();
	private WebServiceTemplate webServiceTemplate = new WebServiceTemplate();

	public FrequencyDistributionServiceSoapProxy(String endpointUrl) {
		this.endpointUrl = endpointUrl;
	}

	public CalculateResponse calculate(CalculateRequest request)
			throws Exception {
		StreamSource source = requestMarshaller.marshall(request);

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		StreamResult result = new StreamResult(out);

		webServiceTemplate.sendSourceAndReceiveToResult(endpointUrl, source,
				result);

		return responseMarshaller.unmarshall(new StreamSource(
				new ByteArrayInputStream(out.toByteArray())));
	}

}
