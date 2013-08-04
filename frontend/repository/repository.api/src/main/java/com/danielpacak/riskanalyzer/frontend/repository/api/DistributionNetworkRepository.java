package com.danielpacak.riskanalyzer.frontend.repository.api;

import com.danielpacak.riskanalyzer.domain.DistributionNetwork;

public interface DistributionNetworkRepository {

	void save(DistributionNetwork network);

	DistributionNetwork read();

}