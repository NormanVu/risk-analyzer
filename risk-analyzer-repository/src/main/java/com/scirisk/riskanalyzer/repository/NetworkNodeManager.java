package com.scirisk.riskanalyzer.repository;

import java.util.List;

import com.scirisk.riskanalyzer.domain.NetworkNode;

public interface NetworkNodeManager {

	NetworkNode save(NetworkNode node);

	NetworkNode findOne(String nodeId);

	void delete(String nodeId);

	List<NetworkNode> findAll();

}