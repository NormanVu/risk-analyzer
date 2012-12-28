package com.danielpacak.riskanalyzer.frontend.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.danielpacak.riskanalyzer.domain.DistributionChannel;
import com.danielpacak.riskanalyzer.domain.DistributionNetwork;
import com.danielpacak.riskanalyzer.domain.Facility;

public class DefaultDistributionNetworkRepository implements DistributionNetworkRepository {

	private FacilityRepository facilityRepository;
	private DistributionChannelRepository distributionChannelRepository;

	public DefaultDistributionNetworkRepository(FacilityRepository facilityRepository,
			DistributionChannelRepository distributionChannelRepository) {
		this.facilityRepository = facilityRepository;
		this.distributionChannelRepository = distributionChannelRepository;
	}

	public DistributionNetwork read() {
		List<Facility> nodes = facilityRepository.findAll();
		List<DistributionChannel> edges = distributionChannelRepository.findAll();
		return new DistributionNetwork(nodes, edges);
	}

	public void save(DistributionNetwork network) {
		Map<String, String> facilityIdMap = new HashMap<String, String>();

		for (Facility node : network.getNodes()) {
			String fakeId = node.getId();
			node.setId(null);
			facilityIdMap.put(fakeId, facilityRepository.save(node).getId());
			node.setId(fakeId);
		}

		for (DistributionChannel ne : network.getEdges()) {
			String fakeSourceId = ne.getSource().getId();
			String fakeTargetId = ne.getTarget().getId();
			String sourceId = facilityIdMap.get(fakeSourceId);
			String targetId = facilityIdMap.get(fakeTargetId);

			distributionChannelRepository.save(ne, sourceId, targetId);
		}
	}

}
