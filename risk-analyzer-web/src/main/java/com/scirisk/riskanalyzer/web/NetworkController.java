package com.scirisk.riskanalyzer.web;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.scirisk.riskanalyzer.domain.Network;
import com.scirisk.riskanalyzer.domain.NetworkEdge;
import com.scirisk.riskanalyzer.domain.NetworkNode;
import com.scirisk.riskanalyzer.persistence.NetworkManager;

@Controller
public class NetworkController {

	@Autowired
	private NetworkManager networkManager;

	public void setNetworkManager(NetworkManager networkManager) {
		this.networkManager = networkManager;
	}

	@RequestMapping(value = "/NetworkMap.do", method = RequestMethod.POST)
	public void getNetworkForGoogleMap(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		response.setContentType("application/json");
		Network network = networkManager.read();
		Collection<NetworkNode> nodes = network.getNodes();
		Collection<NetworkEdge> edges = network.getEdges();
		JSONArray nodesArray = new JSONArray();
		for (NetworkNode nn : nodes) {
			JSONObject o = new JSONObject();
			o.element("id", nn.getId());
			o.element("lat", nn.getLatitude());
			o.element("lng", nn.getLongitude());
			o.element("title", nn.getName());
			o.element("kind", nn.getKind().toString());
			nodesArray.add(o);
		}

		JSONArray edgesArray = new JSONArray();
		for (NetworkEdge e : edges) {
			JSONObject o = new JSONObject();
			o.element("id", e.getId());
			o.element("srcLat", e.getSourceNode().getLatitude());
			o.element("srcLng", e.getSourceNode().getLongitude());
			o.element("trgLat", e.getTargetNode().getLatitude());
			o.element("trgLng", e.getTargetNode().getLongitude());
			edgesArray.add(o);
		}

		JSONObject result = new JSONObject();
		result.element("nodes", nodesArray);
		result.element("edges", edgesArray);
		response.getWriter().write(result.toString());
	}

	@RequestMapping(value = "/NetworkTree.do", method = RequestMethod.GET)
	public void getNetworkForTree(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("application/json");
		Network network = networkManager.read();

		Collection<NetworkNode> nodes = network.getNodes();
		Collection<NetworkEdge> edges = network.getEdges();

		JSONObject nodeFolder = new JSONObject();
		nodeFolder.element("text", "Node");
		nodeFolder.element("cls", "folder");
		nodeFolder.element("expanded", true);
		nodeFolder.element("children", nodesToJson(nodes));

		JSONObject edgeFolder = new JSONObject();
		edgeFolder.element("text", "Edge");
		edgeFolder.element("cls", "folder");
		edgeFolder.element("expanded", true);
		edgeFolder.element("children", edgesToJson(edges));

		JSONArray networkChildren = new JSONArray();
		networkChildren.add(nodeFolder);
		networkChildren.add(edgeFolder);

		JSONObject networkJson = new JSONObject();
		networkJson.element("text", "Network");
		networkJson.element("cls", "folder");
		networkJson.element("expanded", true);
		networkJson.element("children", networkChildren);

		JSONArray root = new JSONArray();
		root.add(networkJson);

		response.getWriter().print(root.toString());
	}

	private JSONArray nodesToJson(final Collection<NetworkNode> nodes) {
		JSONArray nodesArray = new JSONArray();
		for (NetworkNode n : nodes) {
			JSONObject nodeObject = new JSONObject();
			nodeObject.element("id", "n_" + n.getId());
			nodeObject.element("text", n.getName());
			nodeObject.element("leaf", true);
			nodesArray.add(nodeObject);
		}
		return nodesArray;
	}

	private JSONArray edgesToJson(final Collection<NetworkEdge> edges) {
		JSONArray edgesArray = new JSONArray();
		for (NetworkEdge e : edges) {
			JSONObject edgeObject = new JSONObject();
			edgeObject.element("id", "e_" + e.getId());
			final String caption = e.getSourceNode().getName() + " > "
					+ e.getTargetNode().getName();
			edgeObject.element("text", caption);
			edgeObject.element("leaf", true);
			edgesArray.add(edgeObject);
		}
		return edgesArray;
	}

}
