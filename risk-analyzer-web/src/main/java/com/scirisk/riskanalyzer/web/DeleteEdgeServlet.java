package com.scirisk.riskanalyzer.web;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.scirisk.riskanalyzer.persistence.NetworkEdgeManager;
import com.scirisk.riskanalyzer.persistence.jpa.NetworkEdgeManagerJpaImpl;

@SuppressWarnings("serial")
public class DeleteEdgeServlet extends HttpServlet {

  private Logger log = Logger.getLogger(DeleteEdgeServlet.class.getName());

  private NetworkEdgeManager neManager;

  public void init(final ServletConfig config) throws ServletException {
    this.neManager = new NetworkEdgeManagerJpaImpl();
  };

  public void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws IOException, ServletException {
    log.fine("Delete network edge request received..");
    String rawId = req.getParameter("edge_id");
    if (rawId == null) {
      resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing [edge_id] request parameter.");
      return;
    }
    Long id = Long.valueOf(rawId);
    neManager.delete(id);
    log.fine("Delete network edge request processed.");
  }

  public void setNetworkEdgeManager(NetworkEdgeManager edgeManager) {
    this.neManager = edgeManager;
  }

}