package com.scirisk.riskanalyzer.web;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.scirisk.riskanalyzer.persistence.NetworkNodeManager;
import com.scirisk.riskanalyzer.persistence.jpa.NetworkNodeManagerJpaImpl;

@SuppressWarnings("serial")
public class DeleteNodeServlet extends HttpServlet {

  private NetworkNodeManager nnManager;

  public void init(ServletConfig config) throws ServletException {
    this.nnManager = new NetworkNodeManagerJpaImpl();
  };

  public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
    System.out.println("Delete network node request received");
    String rawId = req.getParameter("id"); // TODO CONST
    Long id = Long.valueOf(rawId);
    System.out.println("Network node id: " + id);
    nnManager.delete(id);
  }

}