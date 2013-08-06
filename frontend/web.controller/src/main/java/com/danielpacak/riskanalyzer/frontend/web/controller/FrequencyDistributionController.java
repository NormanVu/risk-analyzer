package com.danielpacak.riskanalyzer.frontend.web.controller;

import static com.danielpacak.riskanalyzer.backend.service.api.FrequencyDistributionService.CONFIDENCE_LEVEL;
import static com.danielpacak.riskanalyzer.backend.service.api.FrequencyDistributionService.NUMBER_OF_ITERATIONS;
import static com.danielpacak.riskanalyzer.backend.service.api.FrequencyDistributionService.TIME_HORIZON;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.danielpacak.riskanalyzer.backend.service.api.CalculateRequest;
import com.danielpacak.riskanalyzer.backend.service.api.CalculateResponse;
import com.danielpacak.riskanalyzer.backend.service.api.FrequencyDistributionService;
import com.danielpacak.riskanalyzer.domain.DistributionNetwork;
import com.danielpacak.riskanalyzer.frontend.repository.api.DistributionNetworkRepository;
import com.danielpacak.riskanalyzer.frontend.web.form.FrequencyDistributionParametersForm;

@Controller
public class FrequencyDistributionController {

	private DistributionNetworkRepository networkRepository;

	private FrequencyDistributionService frequencyDistributionService;

	@Autowired
	public FrequencyDistributionController(DistributionNetworkRepository networkRepository,
			FrequencyDistributionService frequencyDistributionService) {
		this.networkRepository = networkRepository;
		this.frequencyDistributionService = frequencyDistributionService;
	}

	@RequestMapping(value = "/frequency-distribution", method = RequestMethod.POST)
	public @ResponseBody
	FrequencyDistribution calculate(@RequestBody FrequencyDistributionParametersForm form) throws Exception {
		DistributionNetwork network = networkRepository.read();

		Map<String, String> inputParams = new HashMap<String, String>();
		inputParams.put(NUMBER_OF_ITERATIONS, "" + form.getNumberOfIterations());
		inputParams.put(TIME_HORIZON, "" + form.getTimeHorizon());
		inputParams.put(CONFIDENCE_LEVEL, "" + form.getConfidenceLevel());

		// @formatter:off
		CalculateRequest request = new CalculateRequest()
			.setNetwork(network)
			.setInputParams(inputParams);
		// @formatter:on

		return convert(frequencyDistributionService.calculate(request));
	}

	private FrequencyDistribution convert(CalculateResponse response) {
		FrequencyDistribution dist = new FrequencyDistribution();

		List<Point> frequencyDist = new LinkedList<Point>();
		for (double[] point : response.getFrequencyDistribution()) {
			frequencyDist.add(new Point().setX(point[0]).setY(point[1]));
		}
		dist.setFrequencyDistributionData(frequencyDist);

		List<Param> outputParams = new LinkedList<Param>();
		for (Entry<String, String> entry : response.getOutputParams().entrySet()) {
			outputParams.add(new Param().setParam(entry.getKey()).setValue(entry.getValue()));
		}
		dist.setOutputParamsData(outputParams);

		dist.setOutputParamsFormData(response.getOutputParams());
		return dist;
	}

	public static class FrequencyDistribution {

		private List<Point> frequencyDistributionData;

		private List<Param> outputParamsData;

		private Map<String, String> outputParamsFormData;

		public List<Point> getFrequencyDistributionData() {
			return frequencyDistributionData;
		}

		public void setFrequencyDistributionData(List<Point> frequencyDistributionData) {
			this.frequencyDistributionData = frequencyDistributionData;
		}

		public List<Param> getOutputParamsData() {
			return outputParamsData;
		}

		public void setOutputParamsData(List<Param> outputParamsData) {
			this.outputParamsData = outputParamsData;
		}

		public Map<String, String> getOutputParamsFormData() {
			return outputParamsFormData;
		}

		public void setOutputParamsFormData(Map<String, String> outputParamsFormData) {
			this.outputParamsFormData = outputParamsFormData;
		}

	}

	public static class Point {

		private double x;

		private double y;

		public double getX() {
			return x;
		}

		public Point setX(double x) {
			this.x = x;
			return this;
		}

		public double getY() {
			return y;
		}

		public Point setY(double y) {
			this.y = y;
			return this;
		}
	}

	public static class Param {

		private String param;

		private String value;

		public String getParam() {
			return param;
		}

		public Param setParam(String param) {
			this.param = param;
			return this;
		}

		public String getValue() {
			return value;
		}

		public Param setValue(String value) {
			this.value = value;
			return this;
		}
	}

}
