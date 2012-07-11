package com.scirisk.riskanalyzer.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.scirisk.riskanalyzer.entity.NetworkNode;
import com.scirisk.riskanalyzer.repository.NetworkNodeRepository;

public class NetworkServiceImpl implements NetworkService {

	@Autowired
	private NetworkNodeRepository networkNodeRepository;

	public NetworkNode save(NetworkNode networkNode) {
		return this.networkNodeRepository.save(networkNode);
	}

	public void delete(NetworkNode networkNode) {
		this.networkNodeRepository.delete(networkNode);
	}

	public List<NetworkNode> findAll() {
		return networkNodeRepository.findAllNetworkNodes();
	}

}
