package com.scirisk.google.persistence;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Transaction;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.scirisk.riskanalyzer.domain.Facility;
import com.scirisk.riskanalyzer.domain.Facility.Kind;

public class EntityManagerImplTest {

	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(
			new LocalDatastoreServiceTestConfig());

	@Before
	public void beforeTest() {
		helper.setUp();
	}

	@Test
	public void testSave() {
		try {
			DatastoreService datastoreService = Mockito
					.mock(DatastoreService.class);

			EntityManagerImpl entityManager = new EntityManagerImpl(
					datastoreService);

			Facility facility = new Facility();
			facility.setId("");
			facility.setName("Antibes");
			facility.setDescription("Not available now.");
			facility.setKind(Kind.supplier);
			facility.setRecoveryTime1(null);
			
			Mockito.when(datastoreService.put(Mockito.any(Entity.class))).thenReturn(KeyFactory.createKey(Facility.class.getSimpleName(), 13));
			Mockito.when(datastoreService.getCurrentTransaction()).thenReturn(Mockito.mock(Transaction.class));
			
			entityManager.save(facility);

			ArgumentCaptor<Entity> entityArgument = ArgumentCaptor
					.forClass(Entity.class);

			Mockito.verify(datastoreService).put(entityArgument.capture());
			Entity actualEntity = entityArgument.getValue();
			assertEquals(facility.getName(), actualEntity.getProperty("name"));
			assertEquals(facility.getDescription(),
					actualEntity.getProperty("description"));
			assertEquals(facility.getKind().toString(),
					actualEntity.getProperty("kind"));
			assertEquals(facility.getRecoveryTime1(),
					actualEntity.getProperty("recoveryTime1"));
			assertEquals("13", facility.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@After
	public void afterTest() {
		helper.tearDown();
	}

}
