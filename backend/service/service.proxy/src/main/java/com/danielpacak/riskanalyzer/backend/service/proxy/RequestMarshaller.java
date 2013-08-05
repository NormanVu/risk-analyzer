package com.danielpacak.riskanalyzer.backend.service.proxy;

import java.io.IOException;

import javax.xml.transform.stream.StreamSource;

import com.danielpacak.riskanalyzer.backend.service.api.CalculateRequest;


public interface RequestMarshaller {

	StreamSource marshall(CalculateRequest request) throws IOException;

	CalculateRequest unmarshall(StreamSource source) throws IOException;

}
