package com.scirisk.riskanalyzer.web;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.scirisk.riskanalyzer.domain.NetworkNode;
import com.scirisk.riskanalyzer.persistence.NetworkNodeManager;

public class NodeControllerTest {

	NodeController controller;

	@Before
	public void beforeTest() {
		this.controller = new NodeController();
		this.controller.networkNodeManager = Mockito
				.mock(NetworkNodeManager.class);
	}

	@Test
	public void testSave() throws Exception {
		NetworkNode node = new NetworkNode();
		ResponseEntity<String> responseEntity = controller.save(node);
		Mockito.verify(controller.networkNodeManager).save(Mockito.eq(node));
		Assert.assertEquals(HttpStatus.ACCEPTED, responseEntity.getStatusCode());
	}

	@Test
	public void testRead() throws Exception {
		Long nodeId = new Long(13);
		NetworkNode stub = new NetworkNode();
		Mockito.when(controller.networkNodeManager.findOne(nodeId)).thenReturn(
				stub);
		NetworkNode node = controller.read(nodeId);
		Mockito.verify(controller.networkNodeManager).findOne(nodeId);
		Assert.assertEquals(stub, node);
	}

	@Test
	public void testDelete() throws Exception {
		Long nodeId = new Long(13);
		ResponseEntity<String> responseEntity = controller.delete(nodeId);
		Mockito.verify(controller.networkNodeManager).delete(nodeId);
		Assert.assertEquals(HttpStatus.ACCEPTED, responseEntity.getStatusCode());
	}

	@Test
	public void testFindAll() throws Exception {
		List<NetworkNode> stub = Arrays.asList(new NetworkNode());
		Mockito.when(controller.networkNodeManager.findAll()).thenReturn(stub);
		Collection<NetworkNode> allNodes = controller.findAll();
		Assert.assertEquals(stub, allNodes);
	}

}
