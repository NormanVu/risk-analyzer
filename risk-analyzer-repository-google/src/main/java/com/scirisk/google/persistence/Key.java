package com.scirisk.google.persistence;

public class Key<T> {

	private Class<T> entityClass;
	private Long id;

	public Key(Class<T> entityClass, Long id) {
		this.entityClass = entityClass;
		this.id = id;
	}

	public Class<T> getEntityClass() {
		return entityClass;
	}

	public Long getId() {
		return id;
	}

}
