package com.scirisk.riskanalyzer.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.scirisk.riskanalyzer.persistence.ConnectionFactory;
import com.scirisk.riskanalyzer.persistence.NetworkManager;

@SuppressWarnings("serial")
public class TestServlet extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		try {
			out.println("Getting list of nodes...");
			String host = "mysql-dpacak.jelastic.servint.net";
			String database = "risk_analyzer";
			String user = request.getParameter("user");
			String password = request.getParameter("password");
			ConnectionFactory factory = new ConnectionFactory(host, database, user, password);
			NetworkManager manager = new NetworkManager(factory);
			out.println("Hello World!: " + manager.listNodes());
		} catch (Exception e) {
			out.println("Error: " + e.getMessage());
		}
	}

}
