package com.scirisk.riskanalyzer.repository.mongodb;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.scirisk.riskanalyzer.domain.NetworkNode;
import com.scirisk.riskanalyzer.repository.NetworkNodeManager;

public class NetworkNodeManagerMongoDbImpl implements NetworkNodeManager {

	private DB db;

	public NetworkNodeManagerMongoDbImpl(DB db) {
		this.db = db;
	}

	public NetworkNode save(NetworkNode node) {
		DBCollection collection = db.getCollection("networkNodeCollection");
		BasicDBObject nodeObject = new BasicDBObject();
		// String nodeId = UUID.randomUUID().toString();

		nodeObject.put("_id", node.getId() != null ? node.getId() : UUID
				.randomUUID().toString());
		nodeObject.put("kind", node.getKind().toString());
		nodeObject.put("type", node.getType().toString());
		nodeObject.put("name", node.getName());
		nodeObject.put("description", node.getDescription());

		nodeObject.put("address", node.getAddress());
		nodeObject.put("latitude", node.getLatitude());
		nodeObject.put("longitude", node.getLongitude());

		nodeObject.put("riskCategory1", node.getRiskCategory1());
		nodeObject.put("riskCategory2", node.getRiskCategory2());
		nodeObject.put("riskCategory3", node.getRiskCategory3());

		nodeObject.put("recoveryTime1", node.getRecoveryTime1());
		nodeObject.put("recoveryTime2", node.getRecoveryTime2());
		nodeObject.put("recoveryTime3", node.getRecoveryTime3());

		collection.insert(nodeObject);
		return node;
	}

	public NetworkNode findOne(String nodeId) {
		throw new IllegalStateException("I'm not implemented yet");
	}

	public void delete(String nodeId) {
		throw new IllegalStateException("I'm not implemented yet");
	}

	public List<NetworkNode> findAll() {
		DBCollection collection = db.getCollection("networkNodeCollection");
		DBCursor cursor = collection.find();
		List<NetworkNode> nodes = new ArrayList<NetworkNode>();
		try {
			for (DBObject nodeObject : cursor) {
				NetworkNode node = new NetworkNode();
				node.setId((String) nodeObject.get("_id"));
				node.setName((String) nodeObject.get("name"));
				node.setDescription((String) nodeObject.get("Description"));
				nodes.add(node);
			}
			return nodes;
		} finally {
			cursor.close();
		}
	}

}
