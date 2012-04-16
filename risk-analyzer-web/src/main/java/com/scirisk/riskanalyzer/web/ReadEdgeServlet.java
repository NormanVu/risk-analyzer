package com.scirisk.riskanalyzer.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.scirisk.riskanalyzer.domain.NetworkEdge;
import com.scirisk.riskanalyzer.domain.NetworkNode;
import com.scirisk.riskanalyzer.persistence.NetworkEdgeManager;
import com.scirisk.riskanalyzer.persistence.NetworkNodeManager;
import com.scirisk.riskanalyzer.persistence.jpa.NetworkEdgeManagerJpaImpl;
import com.scirisk.riskanalyzer.persistence.jpa.NetworkNodeManagerJpaImpl;

@SuppressWarnings("serial")
public class ReadEdgeServlet extends HttpServlet {

  private NetworkEdgeManager edgeManager;
  private NetworkNodeManager nodeManager;

  public void init(ServletConfig config) throws ServletException {
    this.edgeManager = new NetworkEdgeManagerJpaImpl();
    this.nodeManager = new NetworkNodeManagerJpaImpl();
  };

  public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
    String rawEdgeId = req.getParameter("edge_id");
    Long edgeId = Long.valueOf(rawEdgeId);
    NetworkEdge edge = edgeManager.read(edgeId);
    Collection<NetworkNode> nodes = nodeManager.findAll();

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

    resp.setContentType("text/json");
    PrintWriter out = resp.getWriter();
    out.println(json.toString(2));
  }

  public void setNetworkEdgeManager(NetworkEdgeManager edgeManager) {
    this.edgeManager = edgeManager;
  }

}