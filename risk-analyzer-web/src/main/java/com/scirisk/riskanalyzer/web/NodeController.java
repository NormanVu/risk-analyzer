package com.scirisk.riskanalyzer.web;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.scirisk.riskanalyzer.domain.NetworkNode;
import com.scirisk.riskanalyzer.domain.NetworkNode.Kind;
import com.scirisk.riskanalyzer.domain.NetworkNode.Type;
import com.scirisk.riskanalyzer.persistence.NetworkNodeManager;

@Controller
public class NodeController {

	@Autowired
	private NetworkNodeManager networkNodeManager;

	public void setNetworkNodeManager(NetworkNodeManager networkNodeManager) {
		this.networkNodeManager = networkNodeManager;
	}

	@RequestMapping(value = "/AddNode.do", method = RequestMethod.POST)
	public void createOrUpdateNode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		NetworkNode nn = mapRequestParams(request);
		System.out.println(ReflectionToStringBuilder.toString(nn));
		networkNodeManager.save(nn);
		response.setStatus(HttpServletResponse.SC_CREATED);
	}
	
	@RequestMapping(value = "/ReadNode.do", method = RequestMethod.POST)
	public void readNode(HttpServletRequest request, HttpServletResponse response) throws Exception {
	    String rawNodeId = request.getParameter("node_id"); // TODO VALIDATE
	    Long nodeId = Long.valueOf(rawNodeId);
	    NetworkNode node = networkNodeManager.read(nodeId);

	    JSONObject o = new JSONObject();
	    o.put("node_id", node.getId());
	    o.put("node_name", node.getName());
	    o.put("node_kind", node.getKind());
	    o.put("node_desc", node.getDesc());
	    o.put("node_address", node.getAddress());
	    o.put("node_latitude", node.getLatitude());
	    o.put("node_longitude", node.getLongitude());
	    o.put("node_risk_category_1", node.getRiskCategory1());
	    o.put("node_risk_category_2", node.getRiskCategory2());
	    o.put("node_risk_category_3", node.getRiskCategory3());
	    o.put("node_recovery_time_1", node.getRecoveryTime1());
	    o.put("node_recovery_time_2", node.getRecoveryTime2());
	    o.put("node_recovery_time_3", node.getRecoveryTime3());
	    o.put("node_type", node.getType());

	    response.setContentType("text/json");
	    PrintWriter out = response.getWriter();
	    out.println(o.toString(2));
	}

	public NetworkNode mapRequestParams(HttpServletRequest req) {
		NetworkNode nn = new NetworkNode();

		String nodeIdParam = req.getParameter("node_id");
		if (StringUtils.isNotBlank(nodeIdParam)) {
			nn.setId(Long.valueOf(nodeIdParam));
		}

		nn.setKind(Kind.valueOf(req.getParameter("node_kind")));
		nn.setName(req.getParameter("node_name"));
		nn.setDesc(req.getParameter("node_desc"));

		nn.setAddress(req.getParameter("node_address"));
		nn.setLongitude(Double.valueOf(req.getParameter("node_longitude")));
		nn.setLatitude(Double.valueOf(req.getParameter("node_latitude")));

		nn.setRiskCategory1(Double.valueOf(req
				.getParameter("node_risk_category_1")));
		nn.setRiskCategory2(Double.valueOf(req
				.getParameter("node_risk_category_2")));
		nn.setRiskCategory3(Double.valueOf(req
				.getParameter("node_risk_category_3")));

		nn.setRecoveryTime1(Double.valueOf(req
				.getParameter("node_recovery_time_1")));
		nn.setRecoveryTime2(Double.valueOf(req
				.getParameter("node_recovery_time_2")));
		nn.setRecoveryTime3(Double.valueOf(req
				.getParameter("node_recovery_time_3")));

		nn.setType(Type.valueOf(req.getParameter("node_type")));

		return nn;
	}

}
