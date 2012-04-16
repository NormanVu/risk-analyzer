package com.scirisk.riskanalyzer.web;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import com.scirisk.riskanalyzer.domain.NetworkNode;
import com.scirisk.riskanalyzer.domain.NetworkNode.Kind;
import com.scirisk.riskanalyzer.domain.NetworkNode.Type;
import com.scirisk.riskanalyzer.persistence.NetworkNodeManager;
import com.scirisk.riskanalyzer.persistence.jpa.NetworkNodeManagerJpaImpl;

@SuppressWarnings("serial")
// TODO RENAME THIS CLASS TO SaveNodeServlet
public class AddNodeServlet extends HttpServlet {

  private static Logger log = Logger.getLogger(AddNodeServlet.class.getName());

  private NetworkNodeManager nnManager;

  public void init(final ServletConfig config) throws ServletException {
    this.nnManager = new NetworkNodeManagerJpaImpl();
  };

  public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
    log.fine("New node creation request received");

    NetworkNode nn = mapRequestParams(req);
    System.out.println(ReflectionToStringBuilder.toString(nn));

    Long id = nnManager.save(nn);
    log.fine("New node has been created: " + id);

    resp.setStatus(HttpServletResponse.SC_CREATED);
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

    nn.setRiskCategory1(Double.valueOf(req.getParameter("node_risk_category_1")));
    nn.setRiskCategory2(Double.valueOf(req.getParameter("node_risk_category_2")));
    nn.setRiskCategory3(Double.valueOf(req.getParameter("node_risk_category_3")));

    nn.setRecoveryTime1(Double.valueOf(req.getParameter("node_recovery_time_1")));
    nn.setRecoveryTime2(Double.valueOf(req.getParameter("node_recovery_time_2")));
    nn.setRecoveryTime3(Double.valueOf(req.getParameter("node_recovery_time_3")));

    nn.setType(Type.valueOf(req.getParameter("node_type")));
    
    return nn;
  }

  public void setNetworkNodeManager(NetworkNodeManager nodeManager) {
    this.nnManager = nodeManager;
  }

}