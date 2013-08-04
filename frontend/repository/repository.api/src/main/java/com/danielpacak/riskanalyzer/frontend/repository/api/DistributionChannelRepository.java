package com.danielpacak.riskanalyzer.frontend.repository.api;

import java.util.List;

import com.danielpacak.riskanalyzer.domain.DistributionChannel;

public interface DistributionChannelRepository {

	DistributionChannel save(DistributionChannel distributionChannel, String sourceFacilityId, String targetFacilityId);

	/**
	 * Delete a distribution channel with a given id.
	 * 
	 * @param distributionChannelId
	 *            the id of the distribution channel to be deleted
	 */
	void delete(String distributionChannelId);

	DistributionChannel findOne(String distributionChannelId);

	List<DistributionChannel> findAll();

}
