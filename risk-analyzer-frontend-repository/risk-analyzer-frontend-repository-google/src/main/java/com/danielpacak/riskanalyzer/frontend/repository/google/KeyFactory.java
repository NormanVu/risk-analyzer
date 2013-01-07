package com.danielpacak.riskanalyzer.frontend.repository.google;

import com.google.appengine.api.datastore.Key;

public class KeyFactory {

	Key getKey(String entityName, String entityId) {
		return com.google.appengine.api.datastore.KeyFactory.createKey(entityName, Long.valueOf(entityId));
	}

}
