package com.scirisk.riskanalyzer.backend.service;

import javax.xml.transform.stream.StreamSource;

public interface RAMarshaller {
	
	 StreamSource marshall(CalculateRequest request);
	 CalculateRequest unmarshall(StreamSource source);

}
