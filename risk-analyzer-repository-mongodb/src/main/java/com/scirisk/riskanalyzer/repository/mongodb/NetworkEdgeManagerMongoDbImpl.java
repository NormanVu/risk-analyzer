package com.scirisk.riskanalyzer.repository.mongodb;

import java.util.List;

import com.mongodb.DB;
import com.scirisk.riskanalyzer.domain.NetworkEdge;
import com.scirisk.riskanalyzer.repository.NetworkEdgeManager;

public class NetworkEdgeManagerMongoDbImpl implements NetworkEdgeManager {

	private DB db;

	public NetworkEdgeManagerMongoDbImpl(DB db) {
		this.db = db;
	}

	public NetworkEdge save(NetworkEdge edge, String sourceId, String targetId) {
		throw new IllegalStateException("I'm not implemented yet");
	}

	public void delete(String edgeId) {
		throw new IllegalStateException("I'm not implemented yet");
	}

	public NetworkEdge findOne(String edgeId) {
		throw new IllegalStateException("I'm not implemented yet");
	}

	public List<NetworkEdge> findAll() {
		throw new IllegalStateException("I'm not implemented yet");
	}

}
