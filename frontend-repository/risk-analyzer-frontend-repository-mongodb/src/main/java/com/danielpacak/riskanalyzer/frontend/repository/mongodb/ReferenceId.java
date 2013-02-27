package com.danielpacak.riskanalyzer.frontend.repository.mongodb;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

@SuppressWarnings("serial")
public class ReferenceId<T> extends BasicDBObject {

	private Class<T> clazz;
	private String id;

	public ReferenceId(Class<T> clazz, String id) {
		this.clazz = clazz;
		this.id = id;
		put("ref", clazz.getName());
		put("id", new ObjectId(id));
	}

	public ReferenceId(DBObject dbObject) {
		try {
			this.clazz = (Class<T>) Class.forName((String) dbObject.get("ref"));
			this.id = ((ObjectId) dbObject.get("id")).toString();
		} catch (ClassNotFoundException e) {
			throw new IllegalArgumentException(e); // TODO DESCRIPTIVE MESSAGE
		}
	}

	public String getCollection() {
		return clazz.getName();
	}

	public String getId() {
		return id;
	}

}
