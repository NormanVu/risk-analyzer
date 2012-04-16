package com.scirisk.riskanalyzer.persistence;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collection;

import com.scirisk.riskanalyzer.domain.NetworkNode;

public class NetworkManager {
	
	private ConnectionFactory connectionFactory;
	
	public NetworkManager(ConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
	}
	
	public Collection<NetworkNode> listNodes() {
		Connection connection = connectionFactory.getConnection();
		System.out.println("connection: " + connection);
		return new ArrayList<NetworkNode>();
	}

}
