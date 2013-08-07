package com.danielpacak.riskanalyzer.backend.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.danielpacak.riskanalyzer.backend.service.api.CalculateRequest;
import com.danielpacak.riskanalyzer.backend.service.api.CalculateResponse;
import com.danielpacak.riskanalyzer.backend.service.api.FrequencyDistributionService;

@Controller
public class FrequencyDistributionController {

	private FrequencyDistributionService frequencyDistributionService;

	@Autowired
	public FrequencyDistributionController(FrequencyDistributionService frequencyDistributionService) {
		this.frequencyDistributionService = frequencyDistributionService;
	}

	@RequestMapping(value = "/frequency-distribution", method = RequestMethod.POST)
	public @ResponseBody
	CalculateResponse frequencyDistribution(@RequestBody CalculateRequest request) throws Exception {
		return frequencyDistributionService.calculate(request);
	}

}
