package com.danielpacak.riskanalyzer.backend.service.proxy;

import java.io.IOException;

import javax.xml.transform.stream.StreamSource;

import com.danielpacak.riskanalyzer.backend.service.api.CalculateResponse;


public interface ResponseMarshaller {
	StreamSource marshall(CalculateResponse response) throws IOException;

	CalculateResponse unmarshall(StreamSource source) throws IOException;
}
