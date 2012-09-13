package com.scirisk.riskanalyzer.repository.neo4j;

import org.neo4j.graphdb.GraphDatabaseService;

import com.scirisk.riskanalyzer.domain.DistributionNetwork;
import com.scirisk.riskanalyzer.repository.DistributionNetworkRepository;

public class DistributionNetworkRepositoryNeo4jImpl implements
		DistributionNetworkRepository {

	GraphDatabaseService databaseService;

	public DistributionNetworkRepositoryNeo4jImpl(
			GraphDatabaseService databaseService) {
		this.databaseService = databaseService;
	}

	@Override
	public DistributionNetwork read() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(DistributionNetwork network) {
		// TODO Auto-generated method stub

	}
}
