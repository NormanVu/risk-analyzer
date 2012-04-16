package com.scirisk.riskanalyzer.web;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.scirisk.riskanalyzer.persistence.NetworkEdgeManager;
import com.scirisk.riskanalyzer.persistence.jpa.NetworkEdgeManagerJpaImpl;

@SuppressWarnings("serial")
public class AddEdgeServlet extends HttpServlet {

  private Logger log = Logger.getLogger(AddEdgeServlet.class.getName());

  private NetworkEdgeManager edgeManager;

  public void init(final ServletConfig config) throws ServletException {
    this.edgeManager = new NetworkEdgeManagerJpaImpl();
  };

  public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
    log.fine("Processing add edge request received..");
    if (req.getParameter("edge_source") == null) {
      resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing [edge_source] request parameter.");
      return;
    }
    Long edgeId = getEdgeId(req);
    Long sourceId = Long.valueOf(req.getParameter("edge_source"));
    Long targetId = Long.valueOf(req.getParameter("edge_target"));
    Double purchasingVolume = Double.valueOf(req.getParameter("edge_purchasing_volume"));

    this.edgeManager.save(edgeId, purchasingVolume, sourceId, targetId);

    resp.setStatus(HttpServletResponse.SC_CREATED);
    log.fine("Add edge request processing complete.");
  }

  Long getEdgeId(HttpServletRequest req) {
    String edgeIdParam = req.getParameter("edge_id");
    if (StringUtils.isNotBlank(edgeIdParam)) {
      return Long.valueOf(edgeIdParam);
    } else {
      return null;
    }
  }

  public void setNetworkEdgeManager(NetworkEdgeManager edgeManager) {
    this.edgeManager = edgeManager;
  }

}