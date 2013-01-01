package com.danielpacak.riskanalyzer.frontend.repository.mongodb;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class MongoTemplate {

	public static interface Converter<F, T> {
		T convert(F f);
	}

	private DB db;

	public MongoTemplate(DB db) {
		this.db = db;
	}

	public <T> T insert(T entity, Converter<T, DBObject> writeConverter) {
		DBCollection collection = db.getCollection(determineCollectionName(entity));
		DBObject nodeObject = writeConverter.convert(entity);
		collection.insert(nodeObject);
		return entity;
	}

	public <T> T update(T entity, Converter<T, DBObject> writeConverter) {
		DBCollection collection = db.getCollection(determineCollectionName(entity));
		BasicDBObject query = new BasicDBObject("_id", new ObjectId(getId(entity)));

		BasicDBObject newDocument = new BasicDBObject().append("$set", writeConverter.convert(entity));
		collection.update(query, newDocument);

		return entity;
	}

	public <T> T save(T entity, Converter<T, DBObject> writeConverter) {
		return StringUtils.hasText(getId(entity)) ? update(entity, writeConverter) : insert(entity, writeConverter);
	}

	public <T> T findById(String documentId, Class<T> clazz, Converter<DBObject, T> readConverter) {
		DBCollection collection = db.getCollection(clazz.getName());

		BasicDBObject query = new BasicDBObject("_id", new ObjectId(documentId));

		DBObject document = collection.findOne(query);
		if (document != null) {
			return readConverter.convert(document);
		} else {
			return null;
		}
	}

	public <T> List<T> findAll(Class<T> clazz, Converter<DBObject, T> readConverter) {
		DBCollection collection = db.getCollection(clazz.getName());
		DBCursor cursor = collection.find();
		List<T> mappedDocuments = new ArrayList<T>();
		try {
			for (DBObject document : cursor) {
				mappedDocuments.add(readConverter.convert(document));
			}
			return mappedDocuments;
		} finally {
			cursor.close();
		}
	}

	public <T> void delete(Class<T> clazz, String documentId) {
		DBCollection collection = db.getCollection(clazz.getName());
		DBObject query = new BasicDBObject();
		query.put("_id", new ObjectId(documentId));
		collection.remove(query);
	}

	private String determineCollectionName(Object entity) {
		return entity.getClass().getName();
	}

	private String getId(Object entity) {
		try {
			return (String) BeanUtils.getPropertyDescriptor(entity.getClass(), "id").getReadMethod().invoke(entity);
		} catch (Exception e) {
			throw new IllegalArgumentException("Error getting id of entity: " + entity, e);
		}
	}

}
