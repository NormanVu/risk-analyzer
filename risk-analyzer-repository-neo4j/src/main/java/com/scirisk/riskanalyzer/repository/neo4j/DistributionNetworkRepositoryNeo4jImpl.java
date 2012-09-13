package com.scirisk.riskanalyzer.repository.neo4j;

import com.scirisk.riskanalyzer.domain.DistributionNetwork;
import com.scirisk.riskanalyzer.repository.DistributionChannelRepository;
import com.scirisk.riskanalyzer.repository.DistributionNetworkRepository;
import com.scirisk.riskanalyzer.repository.FacilityRepository;

public class DistributionNetworkRepositoryNeo4jImpl implements
		DistributionNetworkRepository {

	FacilityRepository facilityRepository;
	DistributionChannelRepository distributionChannelRepository;

	public DistributionNetworkRepositoryNeo4jImpl(
			FacilityRepository facilityRepository,
			DistributionChannelRepository distributionChannelRepository) {
		this.facilityRepository = facilityRepository;
		this.distributionChannelRepository = distributionChannelRepository;
	}

	@Override
	public DistributionNetwork read() {
		DistributionNetwork network = new DistributionNetwork();
		network.setNodes(facilityRepository.findAll());
		return network;
	}

	@Override
	public void save(DistributionNetwork network) {
		// TODO Auto-generated method stub

	}
}
