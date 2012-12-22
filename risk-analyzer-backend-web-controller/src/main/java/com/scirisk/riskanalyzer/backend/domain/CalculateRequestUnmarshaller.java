package com.scirisk.riskanalyzer.backend.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom2.Element;
import org.jdom2.Namespace;

import com.scirisk.riskanalyzer.backend.service.CalculateRequest;

public class CalculateRequestUnmarshaller {

  Namespace riskAnalyzerNamespace = Namespace.getNamespace("risk-analyzer", "http://scirisk.com/xml/ns/risk-analyzer");
  Namespace networkNamespace = Namespace.getNamespace("risk-analyzer", "http://scirisk.com/xml/ns/network");

  public CalculateRequest unmarshall(Element calculateRequestElm) {
    System.out.println("Unmarshalling 'CalculateRequest' element: " + calculateRequestElm);
    Element inputParamsElm = calculateRequestElm.getChild("InputParams", riskAnalyzerNamespace);
    Element networkElm = calculateRequestElm.getChild("Network", riskAnalyzerNamespace);
    unmarshallNetwork(networkElm);

    Map<String, String> inputParams = unmarshallInputParams(inputParamsElm);
    System.out.println("inputParams: " + inputParams);

    CalculateRequest request = new CalculateRequest();
    request.setInputParams(inputParams);
    return request;
  }
  
  void unmarshallNetwork(Element networkElm) {
    System.out.println("Unmarshalling 'Network' element: " + networkElm);
    Element nodesElm = networkElm.getChild("nodes", networkNamespace);
    Element edgesElm = networkElm.getChild("edges", networkNamespace);
    unmarshallNodes(nodesElm);
    unmarshallEdges(edgesElm);
  }
  
  void unmarshallNodes(Element nodesElm) {
    System.out.println("Unmarshalling 'nodes' element: " + nodesElm);
    List<Element> nodes = nodesElm.getChildren("node", networkNamespace);
    for (Element nodeElm : nodes) {
      System.out.println("[id: " + nodeElm.getAttributeValue("id")
          + ", name: " + nodeElm.getAttributeValue("name")
          + ", kind: " + nodeElm.getAttributeValue("kind")
          + "]");
    }
  }
  
  void unmarshallEdges(Element edgesElm) {
    System.out.println("Unmarshalling 'edges' element: " + edgesElm);
    List<Element> edges = edgesElm.getChildren("edge", networkNamespace);
    for (Element edgeElm : edges) {
      System.out.println("[source: " + edgeElm.getAttributeValue("source")
          + ", target: " + edgeElm.getAttributeValue("target")
          + ", purchasingVolume: " + edgeElm.getAttributeValue("purchasingVolume")
          + "]");
    }
  }

  Map<String, String> unmarshallInputParams(Element inputParamsElm) {
    Map<String, String> inputParams = new HashMap<String, String>();

    List<Element> parameters = inputParamsElm.getChildren("Parameter", riskAnalyzerNamespace);
    for (Element inputParamElm : parameters) {
      inputParams.put(inputParamElm.getAttributeValue("name"),
          inputParamElm.getAttributeValue("value"));
    }
    return inputParams;
  }

}
