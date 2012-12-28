package com.danielpacak.riskanalyzer.frontend.repository.mongodb;

import com.mongodb.DBObject;

public interface DocumentMapper<T> {

	T map(DBObject document);

}
