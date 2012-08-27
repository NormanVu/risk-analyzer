package com.scirisk.riskanalyzer.persistence;

import java.util.List;

import com.scirisk.riskanalyzer.domain.NetworkEdge;

public interface NetworkEdgeManager {

	void save(NetworkEdge edge, Long sourceId, Long targetId);

	void delete(Long edgeId);

	NetworkEdge findOne(Long edgeId);

	List<NetworkEdge> findAll();

}
