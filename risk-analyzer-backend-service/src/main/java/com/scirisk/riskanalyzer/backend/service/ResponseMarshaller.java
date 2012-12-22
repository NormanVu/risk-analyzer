package com.scirisk.riskanalyzer.backend.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.jdom.Element;
import org.jdom.Namespace;

public class ResponseMarshaller {

	private Namespace namespace = Namespace
			.getNamespace("http://scirisk.com/xml/ns/risk-analyzer");

	public CalculateResponse unmarshall(Element responseElm) {
		System.out.println("Unmarshalling 'CalculateResponse' element: "
				+ responseElm);

		Element distributionElm = responseElm.getChild("FrequencyDistribution",
				namespace);
		Element inputParamsElm = responseElm.getChild("InputParams", namespace);
		Element outputParamsElm = responseElm.getChild("OutputParams",
				namespace);

		CalculateResponse response = new CalculateResponse();
		response.setFrequencyDistribution(unmarshallDistribution(distributionElm));
		response.setOutputParams(unmarshallParams(outputParamsElm));
		response.setInputParams(unmarshallParams(inputParamsElm));

		return response;
	}

	double[][] unmarshallDistribution(Element distributionElm) {
		List<Element> points = distributionElm.getChildren("Point", namespace);
		double[][] distribution = new double[points.size()][2]; // [[x,y],[x,y]]

		for (int i = 0; i < points.size(); i++) {
			Element pointElm = points.get(i);
			double x = Double.valueOf(pointElm.getAttributeValue("X"));
			double y = Double.valueOf(pointElm.getAttributeValue("Y"));
			distribution[i] = new double[] { x, y };
		}
		return distribution;
	}

	Map<String, String> unmarshallParams(Element inputParamsElm) {
		System.out.println("Unmarshalling 'Params' element: " + inputParamsElm);
		Map<String, String> map = new HashMap<String, String>();
		List<Element> parameters = inputParamsElm.getChildren("Parameter",
				namespace);
		for (Element paramElm : parameters) {
			map.put(paramElm.getAttributeValue("name"),
					paramElm.getAttributeValue("value"));
		}
		return map;
	}

	public Element marshall(CalculateResponse response) {
		Element responseElm = new Element("CalculateResponse",
				namespace);
		Element frequencyDistributionElm = createFrequencyDistributionElement(response
				.getFrequencyDistribution());
		Element outputParamsElm = createOutputParamsElement(response
				.getOutputParams());
		Element inputParamsElm = createInputParamsElement(response
				.getInputParams());
		responseElm.addContent(frequencyDistributionElm);
		responseElm.addContent(outputParamsElm);
		responseElm.addContent(inputParamsElm);
		return responseElm;
	}

	Element createFrequencyDistributionElement(double[][] frequencyDistribution) {
		Element frequencyDistributionElm = new Element("FrequencyDistribution",
				namespace);
		for (double[] point : frequencyDistribution) {
			Element pointElm = new Element("Point", namespace);
			pointElm.setAttribute("X", String.valueOf(point[0]));
			pointElm.setAttribute("Y", String.valueOf(point[1]));
			frequencyDistributionElm.addContent(pointElm);
		}
		return frequencyDistributionElm;
	}

	Element createOutputParamsElement(Map<String, String> outputParams) {
		Element outputParamsElm = new Element("OutputParams", namespace);
		for (Entry<String, String> entry : outputParams.entrySet()) {
			Element paramElm = new Element("Parameter", namespace);
			paramElm.setAttribute("name", entry.getKey());
			paramElm.setAttribute("value", entry.getValue());
			outputParamsElm.addContent(paramElm);
		}
		return outputParamsElm;
	}

	Element createInputParamsElement(Map<String, String> inputParams) {
		Element inputParamsElm = new Element("InputParams", namespace);
		for (Entry<String, String> entry : inputParams.entrySet()) {
			Element paramElm = new Element("Parameter", namespace);
			paramElm.setAttribute("name", entry.getKey());
			paramElm.setAttribute("value", entry.getValue());
			inputParamsElm.addContent(paramElm);
		}
		return inputParamsElm;
	}
}
