package com.scirisk.riskanalyzer.web;

import java.io.IOException;
import java.util.Collection;
import java.util.logging.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.scirisk.riskanalyzer.domain.Network;
import com.scirisk.riskanalyzer.domain.NetworkEdge;
import com.scirisk.riskanalyzer.domain.NetworkNode;
import com.scirisk.riskanalyzer.persistence.NetworkManager;
import com.scirisk.riskanalyzer.persistence.jpa.NetworkManagerJpaImpl;

@SuppressWarnings("serial")
public class NetworkTreeServlet extends HttpServlet {

  private Logger log = Logger.getLogger(NetworkTreeServlet.class.getName());

  private NetworkManager networkManager;

  public void init(ServletConfig config) throws ServletException {
    this.networkManager = new NetworkManagerJpaImpl();
  };

  public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
    resp.setContentType("text/json");
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

    resp.getWriter().print(root.toString());
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
      final String caption = e.getSourceNode().getName() + " > " + e.getTargetNode().getName();
      edgeObject.element("text", caption);
      edgeObject.element("leaf", true);
      edgesArray.add(edgeObject);
    }
    return edgesArray;
  }

  public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
    // INGORE
  }

}