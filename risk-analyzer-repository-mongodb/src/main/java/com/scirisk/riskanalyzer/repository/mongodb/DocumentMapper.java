package com.scirisk.riskanalyzer.repository.mongodb;

import com.mongodb.DBObject;

public interface DocumentMapper<T> {

	T map(DBObject document);

}
