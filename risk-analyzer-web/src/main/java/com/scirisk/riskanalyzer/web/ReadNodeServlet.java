package com.scirisk.riskanalyzer.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.scirisk.riskanalyzer.domain.NetworkNode;
import com.scirisk.riskanalyzer.persistence.NetworkNodeManager;
import com.scirisk.riskanalyzer.persistence.jpa.NetworkNodeManagerJpaImpl;

@SuppressWarnings("serial")
public class ReadNodeServlet extends HttpServlet {

  private NetworkNodeManager nodeManager;

  public void init(ServletConfig config) throws ServletException {
    this.nodeManager = new NetworkNodeManagerJpaImpl();
  };

  public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
    String rawNodeId = req.getParameter("node_id"); // TODO VALIDATE
    Long nodeId = Long.valueOf(rawNodeId);
    NetworkNode node = nodeManager.read(nodeId);

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

    resp.setContentType("text/json");
    PrintWriter out = resp.getWriter();
    out.println(o.toString(2));
  }

}