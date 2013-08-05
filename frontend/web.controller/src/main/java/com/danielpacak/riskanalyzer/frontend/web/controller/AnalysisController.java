package com.danielpacak.riskanalyzer.frontend.web.controller;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.danielpacak.riskanalyzer.backend.service.api.CalculateRequest;
import com.danielpacak.riskanalyzer.backend.service.api.CalculateResponse;
import com.danielpacak.riskanalyzer.backend.service.api.FrequencyDistributionService;
import com.danielpacak.riskanalyzer.backend.service.proxy.FrequencyDistributionServiceSoapProxy;
import com.danielpacak.riskanalyzer.domain.DistributionNetwork;
import com.danielpacak.riskanalyzer.frontend.repository.api.DistributionNetworkRepository;

@Controller
public class AnalysisController {

	@Autowired
	private DistributionNetworkRepository networkRepository;

	@RequestMapping(value = "/frequency-distribution", method = RequestMethod.POST)
	public void submit(@RequestBody FrequencyDistributionParameters params, HttpServletResponse resp) throws Exception {
		DistributionNetwork network = networkRepository.read();

		String endpointUrl = params.getEndpointUrl();

		Map<String, String> inputParams = new HashMap<String, String>();
		inputParams.put("number_of_iterations", "" + params.getNumberOfIterations());
		inputParams.put("time_horizon", "" + params.getTimeHorizon());
		inputParams.put("confidence_level", "" + params.getConfidenceLevel());

		JSONObject submitSimulationResponse = null;
		FrequencyDistributionService proxy = new FrequencyDistributionServiceSoapProxy(endpointUrl);

		CalculateRequest request = new CalculateRequest();
		request.setNetwork(network);
		request.setInputParams(inputParams);

		CalculateResponse response = proxy.calculate(request);
		submitSimulationResponse = toJson(response);

		PrintWriter out = resp.getWriter();
		out.println(submitSimulationResponse.toString(2));
	}

	JSONObject toJson(CalculateResponse response) {
		JSONObject object = new JSONObject();

		JSONArray frequencyDist = new JSONArray();
		for (double[] point : response.getFrequencyDistribution()) {
			JSONObject p = new JSONObject();
			p.put("x", point[0]);
			p.put("y", point[1]);
			frequencyDist.add(p);
		}

		JSONArray outputParams = new JSONArray();
		for (Entry<String, String> entry : response.getOutputParams().entrySet()) {
			JSONObject param = new JSONObject();
			param.put("param", entry.getKey());
			param.put("value", entry.getValue());
			outputParams.add(param);
		}

		JSONObject outputParamsFormData = new JSONObject();
		for (Entry<String, String> entry : response.getOutputParams().entrySet()) {
			outputParamsFormData.put(entry.getKey(), entry.getValue());
		}

		object.put("frequencyDistributionData", frequencyDist);
		object.put("outputParamsData", outputParams);
		object.put("outputParamsFormData", outputParamsFormData);
		return object;
	}

}
