package com.danielpacak.riskanalyzer.frontend.repository.mongodb;

import com.mongodb.DBObject;

public interface EntityMapper<T> {

	DBObject map(T entity);

}
