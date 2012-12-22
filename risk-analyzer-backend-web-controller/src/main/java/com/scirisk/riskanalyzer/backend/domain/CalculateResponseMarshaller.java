package com.scirisk.riskanalyzer.backend.domain;

import java.util.Map;
import java.util.Map.Entry;

import org.jdom2.Element;
import org.jdom2.Namespace;

import com.scirisk.riskanalyzer.backend.service.CalculateResponse;

public class CalculateResponseMarshaller {

  Namespace riskAnalyzerNamespace = Namespace.getNamespace("risk-analyzer", "http://scirisk.com/xml/ns/risk-analyzer");
  Namespace networkNamespace = Namespace.getNamespace("risk-analyzer", "http://scirisk.com/xml/ns/network");

  public Element marshall(CalculateResponse response) {
    Element responseElm = new Element("CalculateResponse", riskAnalyzerNamespace);
    Element frequencyDistributionElm = createFrequencyDistributionElement(response.getFrequencyDistribution());
    Element outputParamsElm = createOutputParamsElement(response.getOutputParams());
    Element inputParamsElm = createInputParamsElement(response.getInputParams());
    responseElm.addContent(frequencyDistributionElm);
    responseElm.addContent(outputParamsElm);
    responseElm.addContent(inputParamsElm);
    return responseElm;
  }
  
  Element createFrequencyDistributionElement(double[][] frequencyDistribution) {
    Element frequencyDistributionElm = new Element("FrequencyDistribution", riskAnalyzerNamespace);
    for (double[] point : frequencyDistribution) {
      Element pointElm = new Element("Point", riskAnalyzerNamespace);
      pointElm.setAttribute("X", String.valueOf(point[0]));
      pointElm.setAttribute("Y", String.valueOf(point[1]));
      frequencyDistributionElm.addContent(pointElm);
    }
    return frequencyDistributionElm;
  }

  Element createOutputParamsElement(Map<String, String> outputParams) {
    Element outputParamsElm = new Element("OutputParams", riskAnalyzerNamespace);
    for (Entry<String, String> entry : outputParams.entrySet()) {
      Element paramElm = new Element("Parameter", riskAnalyzerNamespace);
      paramElm.setAttribute("name", entry.getKey());
      paramElm.setAttribute("value", entry.getValue());
      outputParamsElm.addContent(paramElm);
    }
    return outputParamsElm;
  }

  Element createInputParamsElement(Map<String, String> inputParams) {
    Element inputParamsElm = new Element("InputParams", riskAnalyzerNamespace);
    for (Entry<String, String> entry : inputParams.entrySet()) {
      Element paramElm = new Element("Parameter", riskAnalyzerNamespace);
      paramElm.setAttribute("name", entry.getKey());
      paramElm.setAttribute("value", entry.getValue());
      inputParamsElm.addContent(paramElm);
    }
    return inputParamsElm;
  }

}
