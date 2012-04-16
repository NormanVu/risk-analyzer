package com.scirisk.riskanalyzer.persistence;

import java.util.Collection;

import com.scirisk.riskanalyzer.domain.NetworkNode;

public interface NetworkNodeManager {

  Long save(NetworkNode node);

  NetworkNode read(Long nodeId);

  void delete(Long nodeId);

  Collection<NetworkNode> findAll();

}