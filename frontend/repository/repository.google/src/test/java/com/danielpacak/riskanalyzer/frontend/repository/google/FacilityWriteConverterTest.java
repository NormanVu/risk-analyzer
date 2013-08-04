package com.danielpacak.riskanalyzer.frontend.repository.google;

import static com.danielpacak.riskanalyzer.frontend.repository.google.GoogleDatastoreTemplate.DEFAULT_ENTITY_NAME_STRATEGY;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.danielpacak.riskanalyzer.domain.Facility;
import com.danielpacak.riskanalyzer.domain.Facility.Kind;
import com.danielpacak.riskanalyzer.domain.Facility.Type;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

public class FacilityWriteConverterTest {

	LocalServiceTestHelper helper;
	FacilityWriteConverter converter;

	@Before
	public void beforeEachTest() {
		this.converter = new FacilityWriteConverter();
		this.helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());
		this.helper.setUp();
	}

	@After
	public void afterEachTest() {
		this.helper.tearDown();
	}

	@Test
	public void testConvertNullId() throws Exception {
		FacilityWriteConverter writeConverter = new FacilityWriteConverter();
		Facility facility = new Facility.Builder().id("666").kind(Kind.company).type(Type.independent).build();

		Entity entity = writeConverter.convert(facility);
		Assert.assertEquals("company", entity.getProperty("kind"));
		Assert.assertEquals("independent", entity.getProperty("type"));
		Assert.assertEquals(666, entity.getKey().getId());
		Assert.assertEquals(DEFAULT_ENTITY_NAME_STRATEGY.getName(Facility.class), entity.getKey().getKind());
	}

	@Test
	public void testConvertBlankId() throws Exception {
		FacilityWriteConverter writeConverter = new FacilityWriteConverter();
		Facility facility = new Facility.Builder().id(" ").kind(Kind.company).type(Type.independent).build();

		Entity entity = writeConverter.convert(facility);
		Assert.assertEquals("company", entity.getProperty("kind"));
		Assert.assertEquals("independent", entity.getProperty("type"));
		Assert.assertEquals(0, entity.getKey().getId());
		Assert.assertEquals(DEFAULT_ENTITY_NAME_STRATEGY.getName(Facility.class), entity.getKey().getKind());
	}

}
