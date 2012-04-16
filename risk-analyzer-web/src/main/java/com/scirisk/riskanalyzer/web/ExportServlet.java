package com.scirisk.riskanalyzer.web;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.scirisk.riskanalyzer.domain.Network;
import com.scirisk.riskanalyzer.model.NetworkMarshaller;
import com.scirisk.riskanalyzer.model.NetworkMarshallerDomImpl;
import com.scirisk.riskanalyzer.persistence.NetworkManager;
import com.scirisk.riskanalyzer.persistence.jpa.NetworkManagerJpaImpl;

// TODO RENAME TO EXPORT NETWORK SERVLET
@SuppressWarnings("serial")
public class ExportServlet extends HttpServlet {

  private NetworkManager networkManager;
  private NetworkMarshaller marshaller;

  public void init(final ServletConfig config) throws ServletException {
    this.networkManager = new NetworkManagerJpaImpl();
    this.marshaller = new NetworkMarshallerDomImpl();
  };

  public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
    Network network = networkManager.read();
    resp.setContentType("application/xml");
    SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
    String file = "risk-analyzer-network-export-" + dateFormat.format(new Date()) + ".xml";
    resp.setHeader("Content-Disposition", "attachment;filename=" + file);

    marshaller.marshall(network, resp.getOutputStream());
  }

}