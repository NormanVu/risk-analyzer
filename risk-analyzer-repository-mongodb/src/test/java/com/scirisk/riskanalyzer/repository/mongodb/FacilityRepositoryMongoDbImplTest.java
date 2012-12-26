package com.scirisk.riskanalyzer.repository.mongodb;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.scirisk.riskanalyzer.domain.Facility;
import com.scirisk.riskanalyzer.domain.Facility.Kind;
import com.scirisk.riskanalyzer.domain.Facility.Type;

public class FacilityRepositoryMongoDbImplTest {

	DB mockDb;
	DBCollection mockFacilityCollection;
	FacilityRepositoryMongoDbImpl facilityRepository;

	@Before
	public void beforeEachTest() throws Exception {
		this.mockDb = Mockito.mock(DB.class);
		this.mockFacilityCollection = Mockito.mock(DBCollection.class);
		Mockito.when(mockDb.getCollection("facility")).thenReturn(mockFacilityCollection);
		facilityRepository = new FacilityRepositoryMongoDbImpl(mockDb);
	}

	@Test
	public void testDelete() throws Exception {
		String facilityId = "666";

		facilityRepository.delete(facilityId);

		ArgumentCaptor<BasicDBObject> argument = ArgumentCaptor.forClass(BasicDBObject.class);
		Mockito.verify(mockFacilityCollection).remove(argument.capture());
		Assert.assertEquals(facilityId, argument.getValue().getString("_id"));
	}

	@Test
	public void testSave() throws Exception {
		Facility f = new Facility();
		f.setKind(Kind.company);
		f.setType(Type.correlated);

		facilityRepository.save(f);

		ArgumentCaptor<BasicDBObject> argument = ArgumentCaptor.forClass(BasicDBObject.class);

		Mockito.verify(mockFacilityCollection).insert(argument.capture());
		Assert.assertEquals("company", argument.getValue().getString("kind"));
	}

}
