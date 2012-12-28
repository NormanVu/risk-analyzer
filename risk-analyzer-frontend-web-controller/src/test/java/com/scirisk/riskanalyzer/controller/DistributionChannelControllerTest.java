package com.scirisk.riskanalyzer.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.danielpacak.riskanalyzer.domain.DistributionChannel;
import com.danielpacak.riskanalyzer.domain.Facility;
import com.scirisk.riskanalyzer.repository.DistributionChannelRepository;
import com.scirisk.riskanalyzer.repository.FacilityRepository;

public class DistributionChannelControllerTest {

	DistributionChannelController controller;

	@Before
	public void beforeTest() {
		this.controller = new DistributionChannelController();
		this.controller.distributionChannelRepository = mock(DistributionChannelRepository.class);
		this.controller.facilityRepository = mock(FacilityRepository.class);
	}

	@Test
	public void testSave() throws Exception {
		String distributionChannelId = "13";
		Double purchasingVolume = new Double(0.5);
		DistributionChannelFormBean formBean = new DistributionChannelFormBean();
		formBean.setId(distributionChannelId);
		formBean.setPurchasingVolume(purchasingVolume);
		formBean.setSourceId("113");
		formBean.setTargetId("311");

		ResponseEntity<String> responseEntity = controller.save(formBean);
		ArgumentCaptor<DistributionChannel> argument = ArgumentCaptor
				.forClass(DistributionChannel.class);

		verify(controller.distributionChannelRepository).save(
				argument.capture(), eq(formBean.getSourceId()),
				eq(formBean.getTargetId()));
		assertEquals(distributionChannelId, argument.getValue().getId());
		assertEquals(purchasingVolume, argument.getValue()
				.getPurchasingVolume());
		assertEquals(HttpStatus.ACCEPTED, responseEntity.getStatusCode());
	}

	@Test
	public void testRead() throws Exception {
		String distributionChannelId = "13";
		String sourceId = "113";
		String targetId = "311";
		DistributionChannel distributionChannel = new DistributionChannel();
		distributionChannel.setId(distributionChannelId);
		distributionChannel.setPurchasingVolume(0.5);
		Facility source = new Facility();
		source.setId(sourceId);
		Facility target = new Facility();
		target.setId(targetId);
		distributionChannel.setSource(source);
		distributionChannel.setTarget(target);

		List<Facility> facilities = new ArrayList<Facility>();
		when(
				controller.distributionChannelRepository
						.findOne(distributionChannelId)).thenReturn(
				distributionChannel);
		when(controller.facilityRepository.findAll()).thenReturn(facilities);

		DistributionChannelFormBean foundFormBean = controller
				.read(distributionChannelId);
		assertEquals(distributionChannelId, foundFormBean.getId());
		assertEquals(new Double(0.5), foundFormBean.getPurchasingVolume());
		assertEquals(sourceId, foundFormBean.getSourceId());
		assertEquals(targetId, foundFormBean.getTargetId());
		assertEquals(foundFormBean.getFacilities(), facilities);
	}

	@Test
	public void testDelete() throws Exception {
		String distributionChannelId = "13";
		ResponseEntity<String> responseEntity = controller
				.delete(distributionChannelId);
		verify(controller.distributionChannelRepository).delete(
				distributionChannelId);
		assertEquals(HttpStatus.ACCEPTED, responseEntity.getStatusCode());
	}

}
