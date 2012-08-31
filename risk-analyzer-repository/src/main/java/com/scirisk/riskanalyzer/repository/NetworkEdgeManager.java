package com.scirisk.riskanalyzer.repository;

import java.util.List;

import com.scirisk.riskanalyzer.domain.DistributionChannel;

public interface NetworkEdgeManager {

	DistributionChannel save(DistributionChannel edge, String sourceId, String targetId);

	void delete(String edgeId);

	DistributionChannel findOne(String edgeId);

	List<DistributionChannel> findAll();

}
