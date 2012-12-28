package com.scirisk.riskanalyzer.repository;

import com.danielpacak.riskanalyzer.domain.DistributionNetwork;

public interface DistributionNetworkRepository {

	void save(DistributionNetwork network);

	DistributionNetwork read();

}