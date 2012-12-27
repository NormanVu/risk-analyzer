package com.scirisk.riskanalyzer.repository.mongodb;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

@SuppressWarnings("serial")
public class ReferenceId extends BasicDBObject {

	private String collection;
	private String id;

	public ReferenceId(String collection, String id) {
		this.collection = collection;
		this.id = id;
		put("ref", collection);
		put("id", new ObjectId(id));
	}

	public ReferenceId(DBObject dbObject) {
		this.collection = (String) dbObject.get("ref");
		this.id = ((ObjectId) dbObject.get("id")).toString();
	}

	public String getCollection() {
		return collection;
	}

	public String getId() {
		return id;
	}

}
