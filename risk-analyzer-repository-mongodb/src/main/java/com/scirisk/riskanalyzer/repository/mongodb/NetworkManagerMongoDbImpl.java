package com.scirisk.riskanalyzer.repository.mongodb;

import com.mongodb.DB;
import com.scirisk.riskanalyzer.domain.Network;
import com.scirisk.riskanalyzer.repository.NetworkManager;

public class NetworkManagerMongoDbImpl implements NetworkManager {

	private DB db;

	public NetworkManagerMongoDbImpl(DB db) {
		this.db = db;
	}

	public void save(Network network) {
		throw new IllegalStateException("I'm not implemented yet");
	}

	public Network read() {
		throw new IllegalStateException("I'm not implemented yet");
	}

}
