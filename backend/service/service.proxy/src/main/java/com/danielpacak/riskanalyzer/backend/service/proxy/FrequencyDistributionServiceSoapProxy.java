package com.danielpacak.riskanalyzer.backend.service.proxy;

import org.springframework.web.client.RestOperations;

import com.danielpacak.riskanalyzer.backend.service.api.CalculateRequest;
import com.danielpacak.riskanalyzer.backend.service.api.CalculateResponse;
import com.danielpacak.riskanalyzer.backend.service.api.FrequencyDistributionService;

public class FrequencyDistributionServiceSoapProxy implements FrequencyDistributionService {

	private final String baseUri;

	private RestOperations restOperations;

	public FrequencyDistributionServiceSoapProxy(RestOperations restOperations) {
		this(restOperations, "http", "localhost", 8080, "/backend.deployment.dev/api");
	}

	public FrequencyDistributionServiceSoapProxy(RestOperations restOperations, String scheme, String host,
			Integer port, String prefix) {
		this.restOperations = restOperations;
		// @formatter:off
		StringBuilder uri = new StringBuilder()
			.append(scheme)
			.append("://")
			.append(host)
			.append(':')
			.append(port);
		// @formatter:on
		baseUri = prefix != null ? uri.append(prefix).toString() : uri.toString();
	}

	@Override
	public CalculateResponse calculate(CalculateRequest request) throws Exception {
		return restOperations.postForObject(baseUri + "/frequency-distribution", request, CalculateResponse.class);
	}

}
