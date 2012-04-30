package com.scirisk.riskanalyzer.web;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.scirisk.riskanalyzer.domain.NetworkNode;
import com.scirisk.riskanalyzer.persistence.NetworkNodeManager;
import com.scirisk.riskanalyzer.persistence.jpa.NetworkNodeManagerJpaImpl;

// when we want to create an edge, we need to retrieve
// all avaialbe nodes, this servlet is doing right that
@SuppressWarnings("serial")
public class AvailableNodesServlet extends HttpServlet {

  private NetworkNodeManager nnManager;

  public void init(ServletConfig config) throws ServletException {
    this.nnManager = new NetworkNodeManagerJpaImpl();
  };

  public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
    resp.setContentType("application/json");
    PrintWriter out = resp.getWriter();
    Collection<NetworkNode> nodes = nnManager.findAll();
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