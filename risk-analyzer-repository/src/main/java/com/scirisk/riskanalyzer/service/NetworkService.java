package com.scirisk.riskanalyzer.service;

import java.util.List;

import com.scirisk.riskanalyzer.entity.NetworkNode;

public interface NetworkService {

	NetworkNode save(NetworkNode networkNode);

	void delete(NetworkNode networkNode);

	List<NetworkNode> findAll();

}
