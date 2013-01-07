package com.danielpacak.riskanalyzer.frontend.repository.google;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.danielpacak.riskanalyzer.frontend.repository.google.GoogleDatastoreTemplate.Converter;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Transaction;

@RunWith(PowerMockRunner.class)
@PrepareForTest(value = { Entity.class, Key.class })
public class GoogleDatastoreTemplateTest {

	DatastoreService mockDatastoreService;
	KeyFactory mockKeyFactory;
	Transaction mockDatastoreTransaction;

	class Person {

	}

	@Before
	public void beforeEachTest() {
		mockDatastoreService = Mockito.mock(DatastoreService.class);
		mockKeyFactory = Mockito.mock(KeyFactory.class);
		mockDatastoreTransaction = Mockito.mock(Transaction.class);
		Mockito.when(mockDatastoreService.getCurrentTransaction()).thenReturn(mockDatastoreTransaction);
	}

	@Test
	public void testPut() throws Exception {
		GoogleDatastoreTemplate template = new GoogleDatastoreTemplate(mockDatastoreService, mockKeyFactory);

		@SuppressWarnings("unchecked")
		Converter<Person, Entity> stubConverter = Mockito.mock(GoogleDatastoreTemplate.Converter.class);
		Person person = new Person();
		Entity personEntity = PowerMockito.mock(Entity.class);
		Mockito.when(stubConverter.convert(person)).thenReturn(personEntity);

		template.put(person, stubConverter);

		InOrder inOrder = Mockito.inOrder(mockDatastoreService, mockDatastoreTransaction);
		inOrder.verify(mockDatastoreService).beginTransaction();
		inOrder.verify(mockDatastoreService).put(personEntity);
		inOrder.verify(mockDatastoreTransaction).commit();
	}

	@Test
	public void testDelete() throws Exception {
		String personId = "666";

		Key mockKey = Mockito.mock(Key.class);
		Mockito.when(mockKeyFactory.getKey(Person.class.getName(), personId)).thenReturn(mockKey);

		GoogleDatastoreTemplate template = new GoogleDatastoreTemplate(mockDatastoreService, mockKeyFactory);
		template.delete(Person.class, personId);

		InOrder inOrder = Mockito.inOrder(mockDatastoreService, mockDatastoreTransaction);
		inOrder.verify(mockDatastoreService).beginTransaction();
		inOrder.verify(mockDatastoreService).delete(mockKey);
		inOrder.verify(mockDatastoreTransaction).commit();

	}

}
