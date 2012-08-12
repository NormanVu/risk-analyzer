package com.scirisk.riskanalyzer.persistence;

import java.util.List;

import com.scirisk.riskanalyzer.domain.NetworkEdge;

public interface NetworkEdgeManager {

  void save(Long edgeId, Double purchasingVolume, Long sourceId, Long targetId);

  void delete(Long edgeId);

  NetworkEdge read(Long edgeId);

  List<NetworkEdge> findAll();

}
