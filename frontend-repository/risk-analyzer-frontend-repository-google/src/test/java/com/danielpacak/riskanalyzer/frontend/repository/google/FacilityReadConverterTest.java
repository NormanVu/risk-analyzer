package com.danielpacak.riskanalyzer.frontend.repository.google;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.danielpacak.riskanalyzer.domain.Facility;
import com.danielpacak.riskanalyzer.domain.Facility.Kind;
import com.danielpacak.riskanalyzer.domain.Facility.Type;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

public class FacilityReadConverterTest {

	LocalServiceTestHelper helper;
	FacilityReadConverter converter;

	@Before
	public void beforeEachTest() {
		this.converter = new FacilityReadConverter();
		this.helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());
		this.helper.setUp();
	}

	@After
	public void afterEachTest() {
		this.helper.tearDown();
	}

	@Test
	public void testConvert() throws Exception {
		Entity entity = new Entity(KeyFactory.createKey(Facility.class.getName(), 666));
		entity.setProperty("kind", Kind.company.name());
		entity.setProperty("type", Type.correlated.name());
		entity.setProperty("name", "Antibes");
		entity.setProperty("description", "A very nice town.");
		entity.setProperty("address", "Rue Lacan");
		entity.setProperty("latitude", new Double(666.66));
		entity.setProperty("longitude", new Double(999.99));
		entity.setProperty("riskCategory1", new Double(1.0));
		entity.setProperty("recoveryTime1", new Double(100.0));

		Facility facility = converter.convert(entity);

		Assert.assertEquals("666", facility.getId());
		Assert.assertEquals(Kind.company, facility.getKind());
		Assert.assertEquals(Type.correlated, facility.getType());
		Assert.assertEquals("Antibes", facility.getName());
		Assert.assertEquals("A very nice town.", facility.getDescription());
		Assert.assertEquals("Rue Lacan", facility.getAddress());
		Assert.assertEquals(new Double(666.66), facility.getLatitude());
		Assert.assertEquals(new Double(999.99), facility.getLongitude());
		Assert.assertEquals(new Double(1.0), facility.getRiskCategory1());
		Assert.assertEquals(new Double(100.0), facility.getRecoveryTime1());

	}

}
