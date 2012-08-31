package com.scirisk.riskanalyzer.repository.mongodb;

import com.mongodb.DB;
import com.scirisk.riskanalyzer.domain.DistributionNetwork;
import com.scirisk.riskanalyzer.repository.DistributionChannelRepository;
import com.scirisk.riskanalyzer.repository.DistributionNetworkRepository;
import com.scirisk.riskanalyzer.repository.FacilityRepository;

public class NetworkManagerMongoDbImpl implements DistributionNetworkRepository {

	private DB db;

	public NetworkManagerMongoDbImpl(DB db) {
		this.db = db;
	}

	public void save(DistributionNetwork network) {
		throw new IllegalStateException("I'm not implemented yet");
	}

	public DistributionNetwork read() {
		FacilityRepository nodeManager = new NetworkNodeManagerMongoDbImpl(db);
		DistributionChannelRepository edgeManager = new NetworkEdgeManagerMongoDbImpl(db);
		return new DistributionNetwork(nodeManager.findAll(), edgeManager.findAll());
	}

}
