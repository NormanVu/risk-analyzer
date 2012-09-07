package com.scirisk.google.persistence;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.scirisk.riskanalyzer.domain.Facility;

public class DynamicBeanTest {

	@Test
	public void testSetProperty() throws Exception {
		Facility facility = new Facility();

		DynamicBean<Facility> dynamicBean = new DynamicBean<Facility>(facility);
		dynamicBean.setProperty("name", "Antibes");
		Assert.assertEquals("Antibes", facility.getName());
	}

	@Test
	public void testSetProperties() throws Exception {
		Facility facility = new Facility();

		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("name", "Antibes");
		properties.put("description", "No description.");
		properties.put("riskCategory1", new Double(13));

		DynamicBean<Facility> dynamicBean = new DynamicBean<Facility>(facility);
		dynamicBean.setProperties(properties);

		Assert.assertEquals("Antibes", facility.getName());
		Assert.assertEquals("No description.", facility.getDescription());
		Assert.assertEquals(new Double(13), facility.getRiskCategory1());
	}

	@Test
	public void testGetProperty() throws Exception {
		Facility facility = new Facility();
		facility.setName("Antibes");
		facility.setRiskCategory1(new Double(13));

		DynamicBean<Facility> dynamicBean = new DynamicBean<Facility>(facility);
		Assert.assertEquals("Antibes", dynamicBean.getProperty("name"));
		Assert.assertEquals(new Double(13),
				dynamicBean.getProperty("riskCategory1"));
	}
	
	@Test
	public void testGetProperties() throws Exception {
		Facility facility = new Facility();
		facility.setName("Antibes");
		facility.setRiskCategory1(new Double(13));

		DynamicBean<Facility> dynamicBean = new DynamicBean<Facility>(facility);
		Map<String, Object> properties = dynamicBean.getProperties();
		Assert.assertEquals("Antibes", properties.get("name"));
		Assert.assertEquals(new Double(13), properties.get("riskCategory1"));		
	}

}
