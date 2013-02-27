package com.danielpacak.riskanalyzer.frontend.repository;

import java.util.List;

import com.danielpacak.riskanalyzer.domain.DistributionChannel;

public interface DistributionChannelRepository {

	DistributionChannel save(DistributionChannel distributionChannel, String sourceFacilityId, String targetFacilityId);

	void delete(String distributionChannelId);

	DistributionChannel findOne(String distributionChannelId);

	List<DistributionChannel> findAll();

}
