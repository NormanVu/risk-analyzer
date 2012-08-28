package com.scirisk.riskanalyzer.repository.mongodb;

import com.mongodb.DB;
import com.scirisk.riskanalyzer.domain.Network;
import com.scirisk.riskanalyzer.repository.NetworkEdgeManager;
import com.scirisk.riskanalyzer.repository.NetworkManager;
import com.scirisk.riskanalyzer.repository.NetworkNodeManager;

public class NetworkManagerMongoDbImpl implements NetworkManager {

	private DB db;

	public NetworkManagerMongoDbImpl(DB db) {
		this.db = db;
	}

	public void save(Network network) {
		throw new IllegalStateException("I'm not implemented yet");
	}

	public Network read() {
		NetworkNodeManager nodeManager = new NetworkNodeManagerMongoDbImpl(db);
		NetworkEdgeManager edgeManager = new NetworkEdgeManagerMongoDbImpl(db);
		return new Network(nodeManager.findAll(), edgeManager.findAll());
	}

}
