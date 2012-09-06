package com.scirisk.google.persistence;

public interface EntityManager {

	<T> T save(T entity);

	<T> T find(Class<T> entityClass, Object primaryKey);

}
