package com.danielpacak.riskanalyzer.frontend.repository.mongodb;

import org.bson.types.ObjectId;
import org.junit.Assert;
import org.junit.Test;

import com.danielpacak.riskanalyzer.domain.Facility;
import com.danielpacak.riskanalyzer.domain.Facility.Kind;
import com.danielpacak.riskanalyzer.domain.Facility.Type;
import com.mongodb.BasicDBObject;

public class FacilityReadConverterTest {

	@Test
	public void testConvert() throws Exception {
		FacilityReadConverter converter = new FacilityReadConverter();

		ObjectId objectId = new ObjectId();

		BasicDBObject dbObject = new BasicDBObject();
		dbObject.append("_id", objectId);
		dbObject.append("kind", "company");
		dbObject.append("type", "correlated");
		dbObject.append("name", "Antibes");
		dbObject.append("description", "A very nice town in French Riviera");

		Facility facility = converter.convert(dbObject);

		Assert.assertEquals(objectId.toString(), facility.getId());
		Assert.assertEquals(Kind.company, facility.getKind());
		Assert.assertEquals(Type.correlated, facility.getType());
		Assert.assertEquals("Antibes", facility.getName());
		Assert.assertEquals("A very nice town in French Riviera", facility.getDescription());
	}

}
