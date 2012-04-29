package com.scirisk.riskanalyzer.web;

import java.io.PrintWriter;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.scirisk.riskanalyzer.domain.NetworkEdge;
import com.scirisk.riskanalyzer.domain.NetworkNode;
import com.scirisk.riskanalyzer.persistence.NetworkEdgeManager;
import com.scirisk.riskanalyzer.persistence.NetworkNodeManager;

@Controller
public class EdgeController {

	@Autowired
	private NetworkEdgeManager networkEdgeManager;

	@Autowired
	private NetworkNodeManager networkNodeManager;

	public void setNetworkEdgeManager(NetworkEdgeManager networkEdgeManager) {
		this.networkEdgeManager = networkEdgeManager;
	}

	public void setNetworkNodeManager(NetworkNodeManager networkNodeManager) {
		this.networkNodeManager = networkNodeManager;
	}

	@RequestMapping(value = "/AddEdge.do", method = RequestMethod.POST)
	public void createOrUpdate(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Long edgeId = getEdgeId(request);
		Long sourceId = Long.valueOf(request.getParameter("edge_source"));
		Long targetId = Long.valueOf(request.getParameter("edge_target"));
		Double purchasingVolume = Double.valueOf(request
				.getParameter("edge_purchasing_volume"));

		networkEdgeManager.save(edgeId, purchasingVolume, sourceId, targetId);

		response.setStatus(HttpServletResponse.SC_CREATED);
	}

	@RequestMapping(value = "/ReadEdge.do", method = RequestMethod.POST)
	public void read(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String rawEdgeId = request.getParameter("edge_id");
		Long edgeId = Long.valueOf(rawEdgeId);
		NetworkEdge edge = networkEdgeManager.read(edgeId);
		Collection<NetworkNode> nodes = networkNodeManager.findAll();

		JSONObject edgeJson = new JSONObject();
		edgeJson.put("edge_id", edge.getId());
		edgeJson.put("edge_purchasing_volume", edge.getPurchasingVolume());
		edgeJson.put("edge_source", edge.getSourceNode().getId());
		edgeJson.put("edge_target", edge.getTargetNode().getId());

		JSONArray nodesJson = new JSONArray();

		for (NetworkNode n : nodes) {
			JSONObject nodeJson = new JSONObject();
			nodeJson.element("id", n.getId());
			nodeJson.element("name", n.getName());
			nodesJson.add(nodeJson);
		}

		JSONObject json = new JSONObject();
		json.put("edge", edgeJson);
		json.put("nodes", nodesJson);

		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		out.println(json.toString(2));
	}

	@RequestMapping(value = "/DeleteEdge.do", method = RequestMethod.POST)
	public void delete(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String rawId = request.getParameter("edge_id");
		if (rawId == null) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST,
					"Missing [edge_id] request parameter.");
			return;
		}
		Long id = Long.valueOf(rawId);
		networkEdgeManager.delete(id);
		response.setStatus(HttpServletResponse.SC_OK);
	}

	Long getEdgeId(HttpServletRequest req) {
		String edgeIdParam = req.getParameter("edge_id");
		if (StringUtils.isNotBlank(edgeIdParam)) {
			return Long.valueOf(edgeIdParam);
		} else {
			return null;
		}
	}

}
