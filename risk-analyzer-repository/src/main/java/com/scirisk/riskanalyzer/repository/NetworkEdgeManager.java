package com.scirisk.riskanalyzer.repository;

import java.util.List;

import com.scirisk.riskanalyzer.domain.NetworkEdge;

public interface NetworkEdgeManager {

	NetworkEdge save(NetworkEdge edge, String sourceId, String targetId);

	void delete(String edgeId);

	NetworkEdge findOne(String edgeId);

	List<NetworkEdge> findAll();

}
