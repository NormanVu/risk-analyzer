package com.scirisk.riskanalyzer.web;

import java.io.PrintWriter;
import java.util.Collection;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.scirisk.riskanalyzer.domain.NetworkNode;
import com.scirisk.riskanalyzer.persistence.NetworkNodeManager;

@Controller
@RequestMapping(value = "/node")
public class NodeController {

	Logger logger = LoggerFactory.getLogger(NodeController.class);

	@Autowired
	private NetworkNodeManager networkNodeManager;

	@RequestMapping(method = RequestMethod.POST)
	public void save(NetworkNode node, HttpServletResponse response)
			throws Exception {
		networkNodeManager.save(node);
		response.setStatus(HttpServletResponse.SC_CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public @ResponseBody
	NetworkNode read(@PathVariable("id") Long nodeId) throws Exception {
		return networkNodeManager.findOne(nodeId);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable("id") Long nodeId,
			HttpServletResponse response) throws Exception {
		networkNodeManager.delete(nodeId);
	}

	@RequestMapping(method = RequestMethod.GET)
	public void findAll(HttpServletResponse resp) throws Exception {
		resp.setContentType("application/json");
		PrintWriter out = resp.getWriter();
		Collection<NetworkNode> nodes = networkNodeManager.findAll();
		JSONArray array = new JSONArray();

		for (NetworkNode n : nodes) {
			JSONObject object = new JSONObject();
			object.element("id", n.getId());
			object.element("name", n.getName());
			array.add(object);
		}
		out.println(array.toString());
	}

}
