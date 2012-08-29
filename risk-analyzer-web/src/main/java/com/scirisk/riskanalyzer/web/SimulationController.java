package com.scirisk.riskanalyzer.web;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.scirisk.riskanalyzer.backend.proxy.CalculateRequest;
import com.scirisk.riskanalyzer.backend.proxy.CalculateResponse;
import com.scirisk.riskanalyzer.backend.proxy.RiskAnalyzerServiceProxy;
import com.scirisk.riskanalyzer.backend.proxy.RiskAnalyzerServiceProxyImpl;
import com.scirisk.riskanalyzer.domain.Network;
import com.scirisk.riskanalyzer.repository.NetworkManager;

@Controller
public class SimulationController {

	@Autowired
	private NetworkManager networkManager;

	public void setNetworkManager(NetworkManager networkManager) {
		this.networkManager = networkManager;
	}

	@RequestMapping(value = "/SubmitSimulation.do", method = RequestMethod.POST)
	public void submit(HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		Network network = networkManager.read();

		String endpointUrl = req.getParameter("endpoint_url");

		Map<String, String> inputParams = readInputParams(req);
		JSONObject submitSimulationResponse = null;
		try {
			RiskAnalyzerServiceProxy proxy = new RiskAnalyzerServiceProxyImpl(
					endpointUrl);

			CalculateRequest request = new CalculateRequest();
			request.setNetwork(network);
			request.setInputParams(inputParams);

			CalculateResponse response = proxy.calculate(request);
			// System.out.println("response.outputParams: " +
			// response.getOutputParams());
			// System.out.println("response.inputParams: " +
			// response.getInputParams());
			// System.out.println("response.frequencyDistribution: " +
			// response.getFrequencyDistribution());

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

	Map<String, String> readInputParams(HttpServletRequest req) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("number_of_iterations",
				req.getParameter("number_of_iterations"));
		params.put("time_horizon", req.getParameter("time_horizon"));
		params.put("confidence_level", req.getParameter("confidence_level"));
		return params;
	}

}
