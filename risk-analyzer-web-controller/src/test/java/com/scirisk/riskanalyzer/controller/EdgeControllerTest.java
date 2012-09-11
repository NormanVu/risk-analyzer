package com.scirisk.riskanalyzer.controller;

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

import com.scirisk.riskanalyzer.domain.DistributionChannel;
import com.scirisk.riskanalyzer.domain.Facility;
import com.scirisk.riskanalyzer.repository.DistributionChannelRepository;
import com.scirisk.riskanalyzer.repository.FacilityRepository;

public class EdgeControllerTest {

	DistributionChannelController controller;

	@Before
	public void beforeTest() {
		this.controller = new DistributionChannelController();
		this.controller.distributionChannelRepository = mock(DistributionChannelRepository.class);
		this.controller.facilityRepository = mock(FacilityRepository.class);
	}

	@Test
	public void testSave() throws Exception {
		String edgeId = "13";
		Double purchasingVolume = new Double(0.5);
		DistributionChannelFormBean edge = new DistributionChannelFormBean();
		edge.setId(edgeId);
		edge.setPurchasingVolume(purchasingVolume);
		edge.setSourceId("113");
		edge.setTargetId("311");

		ResponseEntity<String> responseEntity = controller.save(edge);
		ArgumentCaptor<DistributionChannel> argument = ArgumentCaptor
				.forClass(DistributionChannel.class);
		verify(controller.distributionChannelRepository).save(argument.capture(),
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
		DistributionChannel stub = new DistributionChannel();
		stub.setId(edgeId);
		stub.setPurchasingVolume(0.5);
		Facility source = new Facility();
		source.setId(sourceId);
		Facility target = new Facility();
		target.setId(targetId);
		stub.setSource(source);
		stub.setTarget(target);

		List<Facility> stubList = new ArrayList<Facility>();
		when(controller.distributionChannelRepository.findOne(edgeId)).thenReturn(stub);
		when(controller.facilityRepository.findAll()).thenReturn(stubList);

		DistributionChannelFormBean edge = controller.read(edgeId);
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
		verify(controller.distributionChannelRepository).delete(nodeId);
		Assert.assertEquals(HttpStatus.ACCEPTED, responseEntity.getStatusCode());
	}

}
