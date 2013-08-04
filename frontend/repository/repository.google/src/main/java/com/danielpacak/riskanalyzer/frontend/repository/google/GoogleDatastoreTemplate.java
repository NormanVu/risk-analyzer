package com.danielpacak.riskanalyzer.frontend.repository.google;

import java.util.ArrayList;
import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class GoogleDatastoreTemplate {

	private DatastoreService datastoreService;
	private KeyFactory keyFactory;

	public GoogleDatastoreTemplate(DatastoreService datastoreService, KeyFactory keyFactory) {
		this.datastoreService = datastoreService;
		this.keyFactory = keyFactory;
	}

	public <T> T put(T entity, Converter<T, Entity> writeConverter) {
		Entity googleEntity = writeConverter.convert(entity);
		datastoreService.beginTransaction();
		datastoreService.put(googleEntity);
		datastoreService.getCurrentTransaction().commit();

		return entity;
	}

	public <T> T findById(String entityId, Class<T> clazz, Converter<Entity, T> readConverter) {
		Key googleKey = keyFactory.getKey(clazz, entityId);
		try {
			Entity googleEntity = datastoreService.get(googleKey);
			return readConverter.convert(googleEntity);
		} catch (EntityNotFoundException e) {
			throw new com.danielpacak.riskanalyzer.frontend.repository.api.EntityNotFoundException(
					"Cannot find entity", e);
		}
	}

	public <T> List<T> findAll(Class<T> clazz, Converter<Entity, T> readConverter) {
		Query q = new Query(DEFAULT_ENTITY_NAME_STRATEGY.getName(clazz));
		PreparedQuery pq = datastoreService.prepare(q);
		List<T> entities = new ArrayList<T>();
		for (Entity googleEntity : pq.asIterable()) {
			entities.add(readConverter.convert(googleEntity));
		}
		return entities;
	}

	public <T> void delete(Class<T> clazz, String entityId) {
		Key entityKey = keyFactory.getKey(clazz, entityId);
		datastoreService.beginTransaction();
		datastoreService.delete(entityKey);
		datastoreService.getCurrentTransaction().commit();
	}

	public static interface Converter<F, T> {
		T convert(F f);
	}

	public static interface EntityNameStrategy {
		String getName(Class<?> entityClass);
	}

	public static EntityNameStrategy DEFAULT_ENTITY_NAME_STRATEGY = new EntityNameStrategy() {
		@Override
		public String getName(Class<?> entityClass) {
			return entityClass.getName();
		}
	};

}
