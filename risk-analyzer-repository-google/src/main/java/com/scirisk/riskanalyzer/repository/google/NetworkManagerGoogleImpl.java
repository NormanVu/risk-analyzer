package com.scirisk.riskanalyzer.repository.google;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.scirisk.riskanalyzer.domain.Network;
import com.scirisk.riskanalyzer.domain.NetworkEdge;
import com.scirisk.riskanalyzer.domain.NetworkNode;
import com.scirisk.riskanalyzer.repository.NetworkEdgeManager;
import com.scirisk.riskanalyzer.repository.NetworkManager;
import com.scirisk.riskanalyzer.repository.NetworkNodeManager;

public class NetworkManagerGoogleImpl implements NetworkManager {

	private NetworkNodeManager nodeManager;
	private NetworkEdgeManager edgeManager;

	public NetworkManagerGoogleImpl() {
		this.nodeManager = new NetworkNodeManagerGoogleImpl();
		this.edgeManager = new NetworkEdgeManagerGoogleImpl();
	}

	public Network read() {
		Collection<NetworkNode> nodes = nodeManager.findAll();
		Collection<NetworkEdge> edges = edgeManager.findAll();
		Network network = new Network(nodes, edges);
		return network;
	}

	public void save(Network network) {
		Map<String, String> nodeIdMap = new HashMap<String, String>();

		for (NetworkNode node : network.getNodes()) {
			String fakeId = node.getId();
			node.setId(null);
			nodeIdMap.put(fakeId, nodeManager.save(node).getId());
			node.setId(fakeId);
		}

		for (NetworkEdge ne : network.getEdges()) {
			String fakeSourceId = ne.getSource().getId();
			String fakeTargetId = ne.getTarget().getId();
			String sourceId = nodeIdMap.get(fakeSourceId);
			String targetId = nodeIdMap.get(fakeTargetId);

			edgeManager.save(ne, sourceId, targetId);
		}
	}

}
