package com.scirisk.riskanalyzer.persistence;

import java.util.List;

import com.scirisk.riskanalyzer.domain.NetworkNode;

public interface NetworkNodeManager {

  Long save(NetworkNode node);

  NetworkNode findOne(Long nodeId);

  void delete(Long nodeId);

  List<NetworkNode> findAll();

}