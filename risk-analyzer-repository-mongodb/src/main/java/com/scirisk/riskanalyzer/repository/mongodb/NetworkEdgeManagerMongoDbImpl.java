package com.scirisk.riskanalyzer.repository.mongodb;

import java.util.List;

import com.scirisk.riskanalyzer.domain.NetworkEdge;
import com.scirisk.riskanalyzer.repository.NetworkEdgeManager;

public class NetworkEdgeManagerMongoDbImpl implements NetworkEdgeManager {

	public void save(NetworkEdge edge, Long sourceId, Long targetId) {
		throw new IllegalStateException("I'm not implemented yet");
	}

	public void delete(Long edgeId) {
		throw new IllegalStateException("I'm not implemented yet");
	}

	public NetworkEdge findOne(Long edgeId) {
		throw new IllegalStateException("I'm not implemented yet");
	}

	public List<NetworkEdge> findAll() {
		throw new IllegalStateException("I'm not implemented yet");
	}

}
