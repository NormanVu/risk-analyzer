package com.scirisk.google.persistence;

public interface EntityManager {

	<T> T save(T entity);

	<T> T find(Key<T> primaryKey);

	<T> void delete(Key<T> primaryKey);

}
