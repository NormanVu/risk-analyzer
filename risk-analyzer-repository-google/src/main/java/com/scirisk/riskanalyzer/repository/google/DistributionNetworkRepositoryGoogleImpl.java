package com.scirisk.riskanalyzer.repository.google;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.scirisk.riskanalyzer.domain.DistributionChannel;
import com.scirisk.riskanalyzer.domain.DistributionNetwork;
import com.scirisk.riskanalyzer.domain.Facility;
import com.scirisk.riskanalyzer.repository.DistributionChannelRepository;
import com.scirisk.riskanalyzer.repository.DistributionNetworkRepository;
import com.scirisk.riskanalyzer.repository.FacilityRepository;

public class DistributionNetworkRepositoryGoogleImpl implements
		DistributionNetworkRepository {

	private FacilityRepository facilityRepository;
	private DistributionChannelRepository distributionChannelRepository;

	public DistributionNetworkRepositoryGoogleImpl(
			FacilityRepository facilityRepository,
			DistributionChannelRepository distributionChannelRepository) {
		this.facilityRepository = facilityRepository;
		this.distributionChannelRepository = distributionChannelRepository;
	}

	public DistributionNetwork read() {
		List<Facility> nodes = facilityRepository.findAll();
		List<DistributionChannel> edges = distributionChannelRepository
				.findAll();
		return new DistributionNetwork(nodes, edges);
	}

	public void save(DistributionNetwork network) {
		Map<String, String> nodeIdMap = new HashMap<String, String>();

		for (Facility node : network.getNodes()) {
			String fakeId = node.getId();
			node.setId(null);
			nodeIdMap.put(fakeId, facilityRepository.save(node).getId());
			node.setId(fakeId);
		}

		for (DistributionChannel ne : network.getEdges()) {
			String fakeSourceId = ne.getSource().getId();
			String fakeTargetId = ne.getTarget().getId();
			String sourceId = nodeIdMap.get(fakeSourceId);
			String targetId = nodeIdMap.get(fakeTargetId);

			distributionChannelRepository.save(ne, sourceId, targetId);
		}
	}

}
