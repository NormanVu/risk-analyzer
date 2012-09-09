package com.scirisk.google.persistence;

import java.util.Map;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class EntityManagerImpl implements EntityManager {

	DatastoreService datastoreService;

	public EntityManagerImpl(DatastoreService datastoreService) {
		this.datastoreService = datastoreService;
	}

	@Override
	public <T> T save(T entity) {
		try {
			Class<? extends Object> entityClass = entity.getClass();

			DynamicBean<T> dynamicEntity = new DynamicBean<T>(entity);
			String rawId = (String) dynamicEntity.getProperty("id");
			Long id = isNotBlank(rawId) ? Long.valueOf(rawId) : null;

			Entity googleEntity = null;

			if (id != null) {
				googleEntity = datastoreService.get(KeyFactory.createKey(
						getEntityKind(entityClass), id));
			} else {
				googleEntity = new Entity(getEntityKind(entityClass));
			}

			Map<String, Object> properties = dynamicEntity.getProperties();
			setProperties(googleEntity, properties);

			datastoreService.beginTransaction();
			Key generatedKey = datastoreService.put(googleEntity);
			datastoreService.getCurrentTransaction().commit();

			dynamicEntity.setProperty("id",
					String.valueOf(generatedKey.getId()));

			return entity;
		} catch (Exception e) {
			throw new RuntimeException("Cannot save entity: " + entity, e);
		}
	}

	@Override
	public <T> T find(com.scirisk.google.persistence.Key<T> primaryKey) {
		Key googleKey = KeyFactory.createKey(
				getEntityKind(primaryKey.getEntityClass()), primaryKey.getId());

		try {
			Entity googleEntity = datastoreService.get(googleKey);

			T entity = primaryKey.getEntityClass().newInstance();
			Map<String, Object> properties = googleEntity.getProperties();
			DynamicBean<T> dynamicBean = new DynamicBean<T>(entity);
			dynamicBean.setProperties(properties);
			dynamicBean.setProperty("id", String.valueOf(googleEntity.getKey().getId()));

			return entity;
		} catch (EntityNotFoundException e) {
			throw new IllegalArgumentException(
					"Cannot find network node entity [" + googleKey + "].");
		} catch (Exception e) {
			throw new RuntimeException("TODO MEANINGFUL MESSAGE", e);
		}
	}

	@Override
	public <T> void delete(com.scirisk.google.persistence.Key<T> primaryKey) {
		Key googleKey = KeyFactory.createKey(
				getEntityKind(primaryKey.getEntityClass()), primaryKey.getId());
		datastoreService.beginTransaction();
		datastoreService.delete(googleKey);
		datastoreService.getCurrentTransaction().commit();
	}

	// move it to class/strategy
	String getEntityKind(Class<?> entityClass) {
		return entityClass.getName();
	}

	void setProperties(Entity entity, Map<String, Object> properties) {
		for (Map.Entry<String, Object> entry : properties.entrySet()) {
			System.out.printf("Setting property %s with value %s%n",
					entry.getKey(), entry.getValue());
			if (isEnum(entry.getValue())) {
				entity.setProperty(entry.getKey(), entry.getValue().toString());
			} else {
				entity.setProperty(entry.getKey(), entry.getValue());
			}
		}
	}

	boolean isEnum(Object propertyValue) {
		return propertyValue != null && propertyValue.getClass().isEnum();
	}

	boolean isNotBlank(String string) {
		return string != null && !"".equals(string);
	}

}
