package com.scirisk.riskanalyzer.controller;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.scirisk.riskanalyzer.domain.Facility;
import com.scirisk.riskanalyzer.repository.FacilityRepository;

public class NodeControllerTest {

	NodeController controller;

	@Before
	public void beforeTest() {
		this.controller = new NodeController();
		this.controller.networkNodeManager = Mockito
				.mock(FacilityRepository.class);
	}

	@Test
	public void testSave() throws Exception {
		Facility node = new Facility();
		ResponseEntity<String> responseEntity = controller.save(node);
		Mockito.verify(controller.networkNodeManager).save(Mockito.eq(node));
		Assert.assertEquals(HttpStatus.ACCEPTED, responseEntity.getStatusCode());
	}

	@Test
	public void testRead() throws Exception {
		String nodeId = "13";
		Facility stub = new Facility();
		Mockito.when(controller.networkNodeManager.findOne(nodeId)).thenReturn(
				stub);
		Facility node = controller.read(nodeId);
		Mockito.verify(controller.networkNodeManager).findOne(nodeId);
		Assert.assertEquals(stub, node);
	}

	@Test
	public void testDelete() throws Exception {
		String nodeId = "13";
		ResponseEntity<String> responseEntity = controller.delete(nodeId);
		Mockito.verify(controller.networkNodeManager).delete(nodeId);
		Assert.assertEquals(HttpStatus.ACCEPTED, responseEntity.getStatusCode());
	}

	@Test
	public void testFindAll() throws Exception {
		List<Facility> stub = Arrays.asList(new Facility());
		Mockito.when(controller.networkNodeManager.findAll()).thenReturn(stub);
		Collection<Facility> allNodes = controller.findAll();
		Assert.assertEquals(stub, allNodes);
	}

}
