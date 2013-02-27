package com.danielpacak.riskanalyzer.frontend.repository;

import com.danielpacak.riskanalyzer.domain.DistributionNetwork;

public interface DistributionNetworkRepository {

	void save(DistributionNetwork network);

	DistributionNetwork read();

}