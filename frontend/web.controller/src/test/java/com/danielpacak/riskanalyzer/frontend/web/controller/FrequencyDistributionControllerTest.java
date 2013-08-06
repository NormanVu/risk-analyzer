package com.danielpacak.riskanalyzer.frontend.web.controller;

import java.util.HashMap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.danielpacak.riskanalyzer.backend.service.api.CalculateRequest;
import com.danielpacak.riskanalyzer.backend.service.api.CalculateResponse;
import com.danielpacak.riskanalyzer.backend.service.api.FrequencyDistributionService;
import com.danielpacak.riskanalyzer.domain.DistributionNetwork;
import com.danielpacak.riskanalyzer.frontend.repository.api.DistributionNetworkRepository;
import com.danielpacak.riskanalyzer.frontend.web.controller.FrequencyDistributionController.FrequencyDistribution;
import com.danielpacak.riskanalyzer.frontend.web.form.FrequencyDistributionParametersForm;

/**
 * Tests for {@link FrequencyDistributionController}.
 */
@RunWith(MockitoJUnitRunner.class)
public class FrequencyDistributionControllerTest {

	@Mock
	DistributionNetworkRepository networkRepository;
	@Mock
	FrequencyDistributionService frequencyDistributionService;

	@Test
	public void testCalculate() throws Exception {
		FrequencyDistributionController controller = new FrequencyDistributionController(networkRepository,
				frequencyDistributionService);

		DistributionNetwork network = new DistributionNetwork();
		CalculateResponse calculateResponse = new CalculateResponse();
		calculateResponse.setFrequencyDistribution(new double[][] {});
		calculateResponse.setOutputParams(new HashMap<String, String>());
		calculateResponse.setInputParams(new HashMap<String, String>());

		Mockito.when(networkRepository.read()).thenReturn(network);
		Mockito.when(frequencyDistributionService.calculate(Mockito.any(CalculateRequest.class))).thenReturn(
				calculateResponse);

		FrequencyDistributionParametersForm form = new FrequencyDistributionParametersForm()
				.setNumberOfIterations(new Long(100)).setTimeHorizon(69).setConfidenceLevel(0.5f);

		FrequencyDistribution frequencyDistribution = controller.calculate(form);
		ArgumentCaptor<CalculateRequest> captor = ArgumentCaptor.forClass(CalculateRequest.class);
		Mockito.verify(frequencyDistributionService).calculate(captor.capture());

	}

}
