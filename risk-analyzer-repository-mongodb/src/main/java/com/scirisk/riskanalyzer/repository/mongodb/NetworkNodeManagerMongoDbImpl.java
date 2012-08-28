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
import com.scirisk.riskanalyzer.domain.NetworkNode.Kind;
import com.scirisk.riskanalyzer.domain.NetworkNode.Type;
import com.scirisk.riskanalyzer.repository.NetworkNodeManager;

public class NetworkNodeManagerMongoDbImpl implements NetworkNodeManager {

	private static final String NETWORK_NODE_COLLECTION = "networkNodeCollection";

	private DB db;

	public NetworkNodeManagerMongoDbImpl(DB db) {
		this.db = db;
	}

	public NetworkNode save(NetworkNode node) {
		DBCollection collection = db.getCollection(NETWORK_NODE_COLLECTION);
		BasicDBObject nodeObject = new BasicDBObject();

		nodeObject.put("_id", isBlank(node.getId()) ? UUID.randomUUID()
				.toString() : node.getId());
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
		DBCollection collection = db.getCollection(NETWORK_NODE_COLLECTION);

		BasicDBObject query = new BasicDBObject();

		query.put("_id", nodeId);

		DBCursor cursor = collection.find(query);
		try {
			if (cursor.iterator().hasNext()) {
				DBObject nodeObject = cursor.iterator().next();
				return map(nodeObject);
			} else {
				return null;
			}
		} finally {
			cursor.close();
		}
	}

	public void delete(String nodeId) {
		DBCollection collection = db.getCollection(NETWORK_NODE_COLLECTION);
		DBObject query = new BasicDBObject();
		query.put("_id", nodeId);
		collection.remove(query);
	}

	public List<NetworkNode> findAll() {
		DBCollection collection = db.getCollection(NETWORK_NODE_COLLECTION);
		DBCursor cursor = collection.find();
		List<NetworkNode> nodes = new ArrayList<NetworkNode>();
		try {
			for (DBObject nodeObject : cursor) {
				nodes.add(map(nodeObject));
			}
			return nodes;
		} finally {
			cursor.close();
		}
	}

	private NetworkNode map(DBObject nodeObject) {
		NetworkNode node = new NetworkNode();
		node.setId((String) nodeObject.get("_id"));
		node.setType(Type.valueOf((String) nodeObject.get("type")));
		node.setKind(Kind.valueOf((String) nodeObject.get("kind")));
		node.setName((String) nodeObject.get("name"));
		node.setDescription((String) nodeObject.get("description"));
		node.setAddress((String) nodeObject.get("address"));
		node.setLatitude((Double) nodeObject.get("latitude"));
		node.setLongitude((Double) nodeObject.get("longitude"));

		node.setRiskCategory1((Double) nodeObject.get("riskCategory1"));
		node.setRiskCategory2((Double) nodeObject.get("riskCategory2"));
		node.setRiskCategory3((Double) nodeObject.get("riskCategory3"));

		node.setRecoveryTime1((Double) nodeObject.get("recoveryTime1"));
		node.setRecoveryTime2((Double) nodeObject.get("recoveryTime2"));
		node.setRecoveryTime3((Double) nodeObject.get("recoveryTime3"));

		return node;
	}

	boolean isBlank(String string) {
		return "".equals(string);
	}

}
