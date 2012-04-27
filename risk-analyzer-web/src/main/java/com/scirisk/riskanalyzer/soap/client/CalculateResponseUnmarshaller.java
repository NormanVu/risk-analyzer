package com.scirisk.riskanalyzer.soap.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom.Element;
import org.jdom.Namespace;

public class CalculateResponseUnmarshaller {

  private Namespace riskAnalyzerNamespace = Namespace.getNamespace("http://scirisk.com/xml/ns/risk-analyzer");

  CalculateResponse unmarshall(Element calculateResponseElm) {
    System.out.println("Unmarshalling 'CalculateResponse' element: " + calculateResponseElm);

    Element frequencyDistributionElm = calculateResponseElm.getChild("FrequencyDistribution", riskAnalyzerNamespace);
    Element inputParamsElm = calculateResponseElm.getChild("InputParams", riskAnalyzerNamespace);
    Element outputParamsElm = calculateResponseElm.getChild("OutputParams", riskAnalyzerNamespace);

    CalculateResponse response = new CalculateResponse();
    response.setFrequencyDistribution(unmarshallFrequencyDistribution(frequencyDistributionElm));
    response.setOutputParams(unmarshallParams(outputParamsElm));
    response.setInputParams(unmarshallParams(inputParamsElm));

    return response;
  }

  List<double[]> unmarshallFrequencyDistribution(Element frequencyDistributionElm) {
    List<Element> points = frequencyDistributionElm.getChildren("Point", riskAnalyzerNamespace);
    List<double[]> result = new ArrayList<double[]>();
    for (Element pointElm : points) {
      double x = Double.valueOf(pointElm.getAttributeValue("X"));
      double y = Double.valueOf(pointElm.getAttributeValue("Y"));
      result.add(new double[] {x, y});
    }
    return result;
  }

  Map<String, String> unmarshallParams(Element inputParamsElm) {
    System.out.println("Unmarshalling 'Params' element: " + inputParamsElm);
    Map<String, String> map = new HashMap<String, String>();
    List<Element> parameters = inputParamsElm.getChildren("Parameter", riskAnalyzerNamespace);
    for (Element paramElm : parameters) {
      map.put(paramElm.getAttributeValue("name"),
          paramElm.getAttributeValue("value"));
    }
    return map;
  }

}
