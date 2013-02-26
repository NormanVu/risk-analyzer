package com.danielpacak.riskanalyzer.backend.service;

import java.util.HashMap;
import java.util.Map;

import com.scirisk.riskanalyzer.backend.proxy.CalculateRequest;
import com.scirisk.riskanalyzer.backend.proxy.CalculateResponse;
import com.scirisk.riskanalyzer.backend.proxy.FrequencyDistributionService;

public class FrequencyDistributionServiceMockImpl implements FrequencyDistributionService {

	private int sleepTimeInMilliseconds = 1000;

	public CalculateResponse calculate(CalculateRequest request) {
		Map<String, String> inputParams = request.getInputParams();
		int steps = Integer.parseInt(inputParams.get("number_of_iterations"));
		calculate(steps);
		CalculateResponse response = new CalculateResponse();
		response.setFrequencyDistribution(frequencyDistribution);
		response.setInputParams(request.getInputParams());
		response.setOutputParams(createMap(outputParams));

		return response;
	}

	private void calculate(int steps) {
		for (int i = 1; i <= steps; i++) {
			System.out.println("HEAVY CALCULATION STEP " + i + " OF " + steps
					+ " ..");
			try {
				Thread.sleep(sleepTimeInMilliseconds); // go to sleep for 30 sec
			} catch (InterruptedException e) {
				// IGNORE
			}
		}
	}

	public void setSleepTimeInMilliseconds(int sleepTimeInMilliseconds) {
		this.sleepTimeInMilliseconds = sleepTimeInMilliseconds;
	}

	Map<String, String> createMap(String[][] params) {
		Map<String, String> map = new HashMap<String, String>();
		for (String[] param : params) {
			map.put(param[0], param[1]);
		}
		return map;
	}

	double[][] frequencyDistribution = { { 0, 0 }, { 1, 0 }, { 2, 0 },
			{ 3, 0 }, { 4, 0 }, { 5, 0 }, { 6, 1 }, { 7, 0 }, { 8, 1 },
			{ 9, 1 }, { 10, 2 }, { 11, 3 }, { 12, 2 }, { 13, 7 }, { 14, 8 },
			{ 15, 15 }, { 16, 22 }, { 17, 27 }, { 18, 42 }, { 19, 52 },
			{ 20, 44 }, { 21, 59 }, { 22, 67 }, { 23, 55 }, { 24, 63 },
			{ 25, 62 }, { 26, 58 }, { 27, 53 }, { 28, 55 }, { 29, 54 },
			{ 30, 57 }, { 31, 33 }, { 32, 33 }, { 33, 22 }, { 34, 26 },
			{ 35, 21 }, { 36, 18 }, { 37, 9 }, { 38, 12 }, { 39, 4 },
			{ 40, 3 }, { 41, 5 }, { 42, 1 }, { 43, 1 }, { 44, 2 }, { 45, 0 },
			{ 46, 0 } };

	String[][] outputParams = { { "Mean", "28.00" }, { "VaR", "41.60" },
			{ "ES", "47.47" }, { "Variance", "63.30" } };

}
