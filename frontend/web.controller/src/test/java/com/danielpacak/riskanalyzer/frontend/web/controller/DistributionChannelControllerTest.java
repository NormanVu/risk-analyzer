package com.danielpacak.riskanalyzer.frontend.web.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.danielpacak.riskanalyzer.domain.DistributionChannel;
import com.danielpacak.riskanalyzer.domain.Facility;
import com.danielpacak.riskanalyzer.frontend.repository.api.DistributionChannelRepository;
import com.danielpacak.riskanalyzer.frontend.repository.api.FacilityRepository;
import com.danielpacak.riskanalyzer.frontend.web.form.DistributionChannelForm;

/**
 * Tests for {@link DistributionChannelController}.
 */
@RunWith(MockitoJUnitRunner.class)
public class DistributionChannelControllerTest {

	@Mock
	DistributionChannelRepository distributionChannelRepository;

	@Mock
	FacilityRepository facilityRepository;

	DistributionChannelController controller;

	@Before
	public void beforeTest() {
		controller = new DistributionChannelController(distributionChannelRepository, facilityRepository);
	}

	@Test
	public void testSave() throws Exception {
		String distributionChannelId = "13";
		Double purchasingVolume = new Double(0.5);
		DistributionChannelForm formBean = new DistributionChannelForm();
		formBean.setId(distributionChannelId);
		formBean.setPurchasingVolume(purchasingVolume);
		formBean.setSourceId("113");
		formBean.setTargetId("311");

		ResponseEntity<String> responseEntity = controller.save(formBean);
		ArgumentCaptor<DistributionChannel> argument = ArgumentCaptor.forClass(DistributionChannel.class);

		verify(distributionChannelRepository).save(argument.capture(), eq(formBean.getSourceId()),
				eq(formBean.getTargetId()));
		assertEquals(distributionChannelId, argument.getValue().getId());
		assertEquals(purchasingVolume, argument.getValue().getPurchasingVolume());
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
		when(distributionChannelRepository.findOne(distributionChannelId)).thenReturn(distributionChannel);
		when(facilityRepository.findAll()).thenReturn(facilities);

		DistributionChannelForm foundFormBean = controller.read(distributionChannelId);
		assertEquals(distributionChannelId, foundFormBean.getId());
		assertEquals(new Double(0.5), foundFormBean.getPurchasingVolume());
		assertEquals(sourceId, foundFormBean.getSourceId());
		assertEquals(targetId, foundFormBean.getTargetId());
		assertEquals(foundFormBean.getFacilities(), facilities);
	}

	@Test
	public void testDelete() throws Exception {
		String distributionChannelId = "13";
		ResponseEntity<String> responseEntity = controller.delete(distributionChannelId);
		verify(distributionChannelRepository).delete(distributionChannelId);
		assertEquals(HttpStatus.ACCEPTED, responseEntity.getStatusCode());
	}

}
