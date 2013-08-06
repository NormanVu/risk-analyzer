package com.danielpacak.riskanalyzer.frontend.service.api;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.danielpacak.riskanalyzer.domain.DistributionNetwork;

public interface NetworkMarshaller {

	void marshall(DistributionNetwork network, OutputStream os) throws IOException;

	public DistributionNetwork unmarshall(InputStream is) throws IOException, NetworkValidationException;

}
