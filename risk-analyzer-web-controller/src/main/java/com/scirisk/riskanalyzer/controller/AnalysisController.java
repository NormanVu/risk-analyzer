package com.scirisk.riskanalyzer.controller;

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

import com.scirisk.riskanalyzer.backend.proxy.CalculateRequest;
import com.scirisk.riskanalyzer.backend.proxy.CalculateResponse;
import com.scirisk.riskanalyzer.backend.proxy.RiskAnalyzerServiceProxy;
import com.scirisk.riskanalyzer.backend.proxy.RiskAnalyzerServiceProxyImpl;
import com.scirisk.riskanalyzer.domain.DistributionNetwork;
import com.scirisk.riskanalyzer.repository.DistributionNetworkRepository;

@Controller
public class AnalysisController {

	@Autowired
	private DistributionNetworkRepository networkRepository;

	@RequestMapping(value = "/frequency-distribution", method = RequestMethod.POST)
	public void submit(@RequestBody FrequencyDistributionParameters params,
			HttpServletResponse resp) throws Exception {
		DistributionNetwork network = networkRepository.read();

		String endpointUrl = params.getEndpointUrl();

		Map<String, String> inputParams = new HashMap<String, String>();
		inputParams.put("number_of_iterations",
				"" + params.getNumberOfIterations());
		inputParams.put("time_horizon", "" + params.getTimeHorizon());
		inputParams.put("confidence_level", "" + params.getConfidenceLevel());

		JSONObject submitSimulationResponse = null;
		try {
			RiskAnalyzerServiceProxy proxy = new RiskAnalyzerServiceProxyImpl(
					endpointUrl);

			CalculateRequest request = new CalculateRequest();
			request.setNetwork(network);
			request.setInputParams(inputParams);

			CalculateResponse response = proxy.calculate(request);
			submitSimulationResponse = toJson(response);
		} catch (Exception e) {
			e.printStackTrace();
			submitSimulationResponse.put("status", "RUNNING");
			submitSimulationResponse.put("message", e.getMessage());
		}

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
		for (Entry<String, String> entry : response.getOutputParams()
				.entrySet()) {
			JSONObject param = new JSONObject();
			param.put("param", entry.getKey());
			param.put("value", entry.getValue());
			outputParams.add(param);
		}

		JSONObject outputParamsFormData = new JSONObject();
		for (Entry<String, String> entry : response.getOutputParams()
				.entrySet()) {
			outputParamsFormData.put(entry.getKey(), entry.getValue());
		}

		object.put("frequencyDistributionData", frequencyDist);
		object.put("outputParamsData", outputParams);
		object.put("outputParamsFormData", outputParamsFormData);
		return object;
	}

}
