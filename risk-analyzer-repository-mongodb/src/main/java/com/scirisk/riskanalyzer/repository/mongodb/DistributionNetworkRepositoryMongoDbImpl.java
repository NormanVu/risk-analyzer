package com.scirisk.riskanalyzer.repository.mongodb;

import com.mongodb.DB;
import com.scirisk.riskanalyzer.domain.DistributionNetwork;
import com.scirisk.riskanalyzer.repository.DistributionChannelRepository;
import com.scirisk.riskanalyzer.repository.DistributionNetworkRepository;
import com.scirisk.riskanalyzer.repository.FacilityRepository;

public class DistributionNetworkRepositoryMongoDbImpl implements DistributionNetworkRepository {

	private DB db;

	public DistributionNetworkRepositoryMongoDbImpl(DB db) {
		this.db = db;
	}

	public void save(DistributionNetwork network) {
		throw new IllegalStateException("I'm not implemented yet");
	}

	public DistributionNetwork read() {
		FacilityRepository nodeManager = new FacilityRepositoryMongoDbImpl(db);
		DistributionChannelRepository edgeManager = new DistributionChannelRepositoryMongoDbImpl(db);
		return new DistributionNetwork(nodeManager.findAll(), edgeManager.findAll());
	}

}
