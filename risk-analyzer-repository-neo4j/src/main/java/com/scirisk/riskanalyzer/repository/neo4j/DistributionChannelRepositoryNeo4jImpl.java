package com.scirisk.riskanalyzer.repository.neo4j;

import java.util.List;

import org.neo4j.graphdb.GraphDatabaseService;

import com.scirisk.riskanalyzer.domain.DistributionChannel;
import com.scirisk.riskanalyzer.repository.DistributionChannelRepository;

public class DistributionChannelRepositoryNeo4jImpl implements
		DistributionChannelRepository {
	GraphDatabaseService databaseService;

	public DistributionChannelRepositoryNeo4jImpl(
			GraphDatabaseService databaseService) {
		this.databaseService = databaseService;
	}

	@Override
	public DistributionChannel save(DistributionChannel edge, String sourceId,
			String targetId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(String edgeId) {
		// TODO Auto-generated method stub

	}

	@Override
	public DistributionChannel findOne(String edgeId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DistributionChannel> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
