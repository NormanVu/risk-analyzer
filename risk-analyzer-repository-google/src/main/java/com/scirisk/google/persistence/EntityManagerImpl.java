package com.scirisk.google.persistence;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.scirisk.riskanalyzer.repository.google.FacilityRepositoryGoogleImpl;

public class EntityManagerImpl implements EntityManager {

	DatastoreService datastoreService;

	public EntityManagerImpl(DatastoreService datastoreService) {
		this.datastoreService = datastoreService;
	}

	@Override
	public <T> T save(T entity) {
		try {
			Class<? extends Object> entityClass = entity.getClass();

			Entity googleEntity = null;
			Long id = getId(entity);

			if (id != null) {
				googleEntity = datastoreService.get(KeyFactory.createKey(getEntityKind(entity), id));
			} else {
				googleEntity = new Entity(getEntityKind(entity));
			}

			BeanInfo beanInfo = Introspector.getBeanInfo(entityClass);

			for (PropertyDescriptor pd : beanInfo.getPropertyDescriptors()) {
				if (pd.getReadMethod() != null && !"class".equals(pd.getName())) {
					setProperty(googleEntity, pd.getName(), pd.getReadMethod()
							.invoke(entity));
				}
			}

			datastoreService.beginTransaction();
			Key generatedKey = datastoreService.put(googleEntity);
			datastoreService.getCurrentTransaction().commit();

			setGeneratedId(entity, generatedKey);

			return entity;
		} catch (Exception e) {
			throw new RuntimeException("Cannot save entity: " + entity, e);
		}
	}

	@Override
	public <T> T find(Class<T> entityClass, Object primaryKey) {
		// TODO Auto-generated method stub
		return null;
	}
	
	String getEntityKind(Object entity) {
		//return entity.getClass().getSimpleName();
		return FacilityRepositoryGoogleImpl.FACILITY_ENTITY;
	}

	<T> Long getId(T entity) throws Exception {
		BeanInfo beanInfo = Introspector.getBeanInfo(entity.getClass());

		for (PropertyDescriptor pd : beanInfo.getPropertyDescriptors()) {
			if ("id".equals(pd.getName())) {
				// todo check type of the id property
				String id = (String) pd.getReadMethod().invoke(entity);
				return id != null && !"".equals(id) ? Long.valueOf((String) id) : null;
			}
		}
		return null; // throws exception because we couldn't find id property
	}

	<T> void setGeneratedId(T entity, Key generatedKey) throws Exception {
		BeanInfo beanInfo = Introspector.getBeanInfo(entity.getClass());

		for (PropertyDescriptor pd : beanInfo.getPropertyDescriptors()) {
			if ("id".equals(pd.getName())) {
				System.out.printf("Setting genereted id: %s%n",
						generatedKey.getId());
				pd.getWriteMethod().invoke(entity,
						String.valueOf(generatedKey.getId()));
				return;
			}
		}
	}

	void setProperty(Entity entity, String propertyName, Object propertyValue) {
		System.out.printf("Setting property %s with value %s%n", propertyName,
				propertyValue);
		if (isEnum(propertyValue)) {
			entity.setProperty(propertyName, propertyValue.toString());
		} else {
			entity.setProperty(propertyName, propertyValue);
		}
	}

	boolean isEnum(Object propertyValue) {
		return propertyValue != null && propertyValue.getClass().isEnum();
	}

	boolean isGetter(String methodName) {
		return methodName.startsWith("get");
	}

	String getPropertyNameFromMethodName(String methodName) {
		String withoutGetPrefix = methodName.substring(3);
		return uncapitalize(withoutGetPrefix);
	}

	String uncapitalize(String string) {
		return new StringBuilder()
				.append(Character.toLowerCase(string.charAt(0)))
				.append(string.length() > 1 ? string.substring(1) : "")
				.toString();
	}

}
