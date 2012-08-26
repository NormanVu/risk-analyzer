package com.scirisk.riskanalyzer.web;

import java.io.PrintWriter;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.scirisk.riskanalyzer.domain.NetworkEdge;
import com.scirisk.riskanalyzer.domain.NetworkNode;
import com.scirisk.riskanalyzer.persistence.NetworkEdgeManager;
import com.scirisk.riskanalyzer.persistence.NetworkNodeManager;

@Controller
@RequestMapping(value = "/edge")
public class EdgeController {

	Logger logger = LoggerFactory.getLogger(EdgeController.class);

	@Autowired
	NetworkEdgeManager networkEdgeManager;

	@Autowired
	NetworkNodeManager networkNodeManager;

	@RequestMapping(method = RequestMethod.POST)
	public void save(@RequestParam("edge_id") Long edgeId, @RequestParam("edge_source") Long sourceId, @RequestParam("edge_target") Long targetId, @RequestParam("edge_purchasing_volume") Double purchasingVolume, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		networkEdgeManager.save(edgeId, purchasingVolume, sourceId, targetId);
		response.setStatus(HttpServletResponse.SC_CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public void read(@PathVariable("id") Long edgeId,
			HttpServletResponse response) throws Exception {
		NetworkEdge edge = networkEdgeManager.read(edgeId);
		Collection<NetworkNode> nodes = networkNodeManager.findAll();

		JSONObject edgeJson = new JSONObject();
		edgeJson.put("edge_id", edge.getId());
		edgeJson.put("edge_purchasing_volume", edge.getPurchasingVolume());
		edgeJson.put("edge_source", edge.getSource().getId());
		edgeJson.put("edge_target", edge.getTarget().getId());

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

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> delete(@PathVariable("id") Long edgeId)
			throws Exception {
		networkEdgeManager.delete(edgeId);
		return new ResponseEntity<String>(HttpStatus.ACCEPTED);
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
