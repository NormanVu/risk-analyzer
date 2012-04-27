package com.scirisk.riskanalyzer.web;

import java.io.IOException;
import java.util.Collection;

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
public class NetworkMapServlet extends HttpServlet {

  private NetworkManager networkManager;

  public void init(final ServletConfig config) throws ServletException {
    this.networkManager = new NetworkManagerJpaImpl();
  };

  public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
    resp.setContentType("text/json");
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
    resp.getWriter().write(result.toString());
  }

}
