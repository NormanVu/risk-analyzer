package com.scirisk.riskanalyzer.web;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.scirisk.riskanalyzer.persistence.NetworkEdgeManager;

public class EdgeControllerTest {

	EdgeController controller;

	@Before
	public void beforeTest() {
		this.controller = new EdgeController();
		this.controller.networkEdgeManager = Mockito
				.mock(NetworkEdgeManager.class);
	}

	@Test
	public void testDelete() throws Exception {
		Long nodeId = new Long(13);
		ResponseEntity<String> responseEntity = controller.delete(nodeId);
		Mockito.verify(controller.networkEdgeManager).delete(nodeId);
		Assert.assertEquals(HttpStatus.ACCEPTED, responseEntity.getStatusCode());
	}

}
