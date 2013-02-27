package com.scirisk.riskanalyzer.service;

import java.io.OutputStream;

import com.danielpacak.riskanalyzer.domain.DistributionNetwork;

public interface NetworkMarshaller {

	void marshall(DistributionNetwork network, OutputStream os);

}