package com.danielpacak.riskanalyzer.frontend.web.controller;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Tests for {@link IndexController}.
 */
public class IndexControllerTest {

	@Test
	public void testIndex() throws Exception {
		IndexController controller = new IndexController();
		assertEquals("WEB-INF/view/index.jsp", controller.index());
	}

}
