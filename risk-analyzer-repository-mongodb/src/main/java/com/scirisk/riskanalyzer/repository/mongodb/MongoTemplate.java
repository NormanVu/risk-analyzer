package com.scirisk.riskanalyzer.repository.mongodb;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class MongoTemplate {

	private DB db;

	public MongoTemplate(DB db) {
		this.db = db;
	}

	public <T> T insert(String collectionName, T entity, EntityMapper<T> entityMapper) {
		DBCollection collection = db.getCollection(collectionName);
		DBObject nodeObject = entityMapper.map(entity);
		collection.insert(nodeObject);
		return entity;
	}

	public <T> T findOne(String collectionName, String documentId, DocumentMapper<T> documentMapper) {
		DBCollection collection = db.getCollection(collectionName);

		BasicDBObject query = new BasicDBObject("_id", new ObjectId(documentId));

		DBObject document = collection.findOne(query);
		if (document != null) {
			return documentMapper.map(document);
		} else {
			return null;
		}
	}

	public <T> List<T> findAll(String collectionName, DocumentMapper<T> documentMapper) {
		DBCollection collection = db.getCollection(collectionName);
		DBCursor cursor = collection.find(); // TODO CHECK IF COLLECTION EXISTS
		List<T> mappedDocuments = new ArrayList<T>();
		try {
			for (DBObject document : cursor) {
				mappedDocuments.add(documentMapper.map(document));
			}
			return mappedDocuments;
		} finally {
			cursor.close();
		}
	}

	public void delete(String collectionName, String documentId) {
		DBCollection collection = db.getCollection(collectionName);
		DBObject query = new BasicDBObject();
		query.put("_id", new ObjectId(documentId));
		collection.remove(query);
	}

}
