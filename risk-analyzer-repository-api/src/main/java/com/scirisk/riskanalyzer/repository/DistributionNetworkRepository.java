package com.scirisk.riskanalyzer.repository;

import com.scirisk.riskanalyzer.domain.DistributionNetwork;

public interface DistributionNetworkRepository {

	void save(DistributionNetwork network);

	DistributionNetwork read();

}