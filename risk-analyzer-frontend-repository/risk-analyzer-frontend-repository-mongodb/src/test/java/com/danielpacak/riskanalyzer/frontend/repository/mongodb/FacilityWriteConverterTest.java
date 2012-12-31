package com.danielpacak.riskanalyzer.frontend.repository.mongodb;

import org.junit.Assert;
import org.junit.Test;

import com.danielpacak.riskanalyzer.domain.Facility;
import com.danielpacak.riskanalyzer.domain.Facility.Kind;
import com.danielpacak.riskanalyzer.domain.Facility.Type;
import com.mongodb.DBObject;

public class FacilityWriteConverterTest {
	
	@Test
	public void testConvert() throws Exception {
		FacilityWriteConverter converter = new FacilityWriteConverter();
		
		Facility facility = new Facility();
		facility.setKind(Kind.company);
		facility.setType(Type.independent);
		
		DBObject dbObject = converter.convert(facility);
		Assert.assertEquals("company", dbObject.get("kind"));
		Assert.assertEquals("independent", dbObject.get("type"));
		
	}

}
