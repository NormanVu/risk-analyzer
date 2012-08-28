package com.scirisk.riskanalyzer.web;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.scirisk.riskanalyzer.domain.NetworkEdge;
import com.scirisk.riskanalyzer.domain.NetworkNode;
import com.scirisk.riskanalyzer.repository.NetworkEdgeManager;
import com.scirisk.riskanalyzer.repository.NetworkNodeManager;
import com.scirisk.riskanalyzer.web.bean.NetworkEdgeFormBean;

public class EdgeControllerTest {

	EdgeController controller;

	@Before
	public void beforeTest() {
		this.controller = new EdgeController();
		this.controller.networkEdgeManager = mock(NetworkEdgeManager.class);
		this.controller.networkNodeManager = mock(NetworkNodeManager.class);
	}

	@Test
	public void testSave() throws Exception {
		String edgeId = "13";
		Double purchasingVolume = new Double(0.5);
		NetworkEdgeFormBean edge = new NetworkEdgeFormBean();
		edge.setId(edgeId);
		edge.setPurchasingVolume(purchasingVolume);
		edge.setSourceId("113");
		edge.setTargetId("311");

		ResponseEntity<String> responseEntity = controller.save(edge);
		ArgumentCaptor<NetworkEdge> argument = ArgumentCaptor
				.forClass(NetworkEdge.class);
		verify(controller.networkEdgeManager).save(argument.capture(),
				eq(edge.getSourceId()), eq(edge.getTargetId()));
		Assert.assertEquals(edgeId, argument.getValue().getId());
		Assert.assertEquals(purchasingVolume, argument.getValue()
				.getPurchasingVolume());
		Assert.assertEquals(HttpStatus.ACCEPTED, responseEntity.getStatusCode());
	}

	@Test
	public void testRead() throws Exception {
		String edgeId = "13";
		String sourceId = "113";
		String targetId = "311";
		NetworkEdge stub = new NetworkEdge();
		stub.setId(edgeId);
		stub.setPurchasingVolume(0.5);
		NetworkNode source = new NetworkNode();
		source.setId(sourceId);
		NetworkNode target = new NetworkNode();
		target.setId(targetId);
		stub.setSource(source);
		stub.setTarget(target);

		List<NetworkNode> stubList = new ArrayList<NetworkNode>();
		when(controller.networkEdgeManager.findOne(edgeId)).thenReturn(stub);
		when(controller.networkNodeManager.findAll()).thenReturn(stubList);

		NetworkEdgeFormBean edge = controller.read(edgeId);
		Assert.assertEquals(edgeId, edge.getId());
		Assert.assertEquals(new Double(0.5), edge.getPurchasingVolume());
		Assert.assertEquals(sourceId, edge.getSourceId());
		Assert.assertEquals(targetId, edge.getTargetId());
		Assert.assertEquals(edge.getNodes(), stubList);
	}

	@Test
	public void testDelete() throws Exception {
		String nodeId = "13";
		ResponseEntity<String> responseEntity = controller.delete(nodeId);
		verify(controller.networkEdgeManager).delete(nodeId);
		Assert.assertEquals(HttpStatus.ACCEPTED, responseEntity.getStatusCode());
	}

}
