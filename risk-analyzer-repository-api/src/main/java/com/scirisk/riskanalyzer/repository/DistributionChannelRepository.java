package com.scirisk.riskanalyzer.repository;

import java.util.List;

import com.scirisk.riskanalyzer.domain.DistributionChannel;

public interface DistributionChannelRepository {

	DistributionChannel save(DistributionChannel distributionChannel, String sourceId,
			String targetId);

	void delete(String distributionChannelId);

	DistributionChannel findOne(String distributionChannelId);

	List<DistributionChannel> findAll();

}
