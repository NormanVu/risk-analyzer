package com.scirisk.learning;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;

import junit.framework.Assert;

import org.junit.Test;

import com.scirisk.riskanalyzer.domain.Facility;

public class ReflectionLearningTest {

	@Test
	public void testMe() throws Exception {
		Facility facility = new Facility();
		facility.setName("Antibes");

		for (PropertyDescriptor pd : Introspector.getBeanInfo(Facility.class)
				.getPropertyDescriptors()) {
			if (pd.getReadMethod() != null && !"class".equals(pd.getName()))
				System.out.println(pd.getReadMethod().invoke(facility));
			
		}
	}
	
	@Test
	public void testIntrospectorDecapitalize() throws Exception {
		String subjectString = "SomeInputString";
		Assert.assertEquals("someInputString", java.beans.Introspector.decapitalize(subjectString));
	}

}
