package com.scirisk.riskanalyzer.repository;

import com.scirisk.riskanalyzer.domain.DistributionNetwork;

public interface NetworkManager {

	void save(DistributionNetwork network);

	DistributionNetwork read();

}