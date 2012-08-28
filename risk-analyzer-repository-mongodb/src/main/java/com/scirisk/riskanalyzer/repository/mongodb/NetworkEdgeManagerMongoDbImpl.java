package com.scirisk.riskanalyzer.repository.mongodb;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.scirisk.riskanalyzer.domain.NetworkEdge;
import com.scirisk.riskanalyzer.repository.NetworkEdgeManager;
import com.scirisk.riskanalyzer.repository.NetworkNodeManager;

public class NetworkEdgeManagerMongoDbImpl implements NetworkEdgeManager {

	private static final String NETWORK_EDGE_COLLECTION = "networkEdgeCollection";

	private DB db;

	public NetworkEdgeManagerMongoDbImpl(DB db) {
		this.db = db;
	}

	public NetworkEdge save(NetworkEdge edge, String sourceId, String targetId) {
		DBCollection collection = db.getCollection(NETWORK_EDGE_COLLECTION);
		BasicDBObject edgeObject = new BasicDBObject();

		edgeObject.put("_id", isBlank(edge.getId()) ? UUID.randomUUID()
				.toString() : edge.getId());
		edgeObject.put("purchasingVolume", edge.getPurchasingVolume());
		edgeObject.put("sourceId", sourceId);
		edgeObject.put("targetId", targetId);

		collection.insert(edgeObject);
		return edge;
	}

	public void delete(String edgeId) {
		DBCollection collection = db.getCollection(NETWORK_EDGE_COLLECTION);
		DBObject query = new BasicDBObject();
		query.put("_id", edgeId);
		collection.remove(query);
	}

	public NetworkEdge findOne(String edgeId) {
		DBCollection collection = db.getCollection(NETWORK_EDGE_COLLECTION);

		BasicDBObject query = new BasicDBObject();

		query.put("_id", edgeId);

		DBCursor cursor = collection.find(query);
		try {
			if (cursor.iterator().hasNext()) {
				DBObject edgeObject = cursor.iterator().next();
				return map(edgeObject);
			} else {
				return null;
			}
		} finally {
			cursor.close();
		}
	}

	public List<NetworkEdge> findAll() {
		DBCollection collection = db.getCollection(NETWORK_EDGE_COLLECTION);
		DBCursor cursor = collection.find();
		List<NetworkEdge> edges = new ArrayList<NetworkEdge>();
		try {
			for (DBObject edgeObject : cursor) {
				edges.add(map(edgeObject));
			}
			return edges;
		} finally {
			cursor.close();
		}
	}

	private NetworkEdge map(DBObject edgeObject) {
		NetworkEdge edge = new NetworkEdge();
		edge.setId((String) edgeObject.get("_id"));
		edge.setPurchasingVolume((Double) edgeObject.get("purchasingVolume"));
		NetworkNodeManager nodeManager = new NetworkNodeManagerMongoDbImpl(db);
		edge.setSource(nodeManager.findOne((String) edgeObject.get("sourceId")));
		edge.setTarget(nodeManager.findOne((String) edgeObject.get("targetId")));
		return edge;
	}

	boolean isBlank(String string) {
		return "".equals(string);
	}

}
