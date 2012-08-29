package com.scirisk.riskanalyzer.backend.proxy;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

import org.jdom.Element;
import org.jdom.Namespace;

import com.scirisk.riskanalyzer.domain.Network;
import com.scirisk.riskanalyzer.domain.NetworkEdge;
import com.scirisk.riskanalyzer.domain.NetworkNode;

public class CalculateRequestMarshaller {

  private Namespace riskAnalyzerNamespace = Namespace.getNamespace("http://scirisk.com/xml/ns/risk-analyzer");
  private Namespace networkNamespace = Namespace.getNamespace("http://scirisk.com/xml/ns/network");

  Element marshall(CalculateRequest request) {
    Element calculateRequestElm = new Element("CalculateRequest", riskAnalyzerNamespace);

    Element networkElm = marshallNetwork(request.getNetwork());
    Element inputParamsElm = marshallInputParams(request.getInputParams());
    
    calculateRequestElm.addContent(networkElm);
    calculateRequestElm.addContent(inputParamsElm);

    return calculateRequestElm;
  }

  Element marshallNetwork(Network network) {
    Element networkElm = new Element("Network", riskAnalyzerNamespace);
    Element nodesElm = marshallNodes(network.getNodes());
    Element edgesElm = marshallEdges(network.getEdges());
    networkElm.addContent(nodesElm);
    networkElm.addContent(edgesElm);
    return networkElm;
  }

  Element marshallNodes(Collection<NetworkNode> nodes) {
    Element nodesElm = new Element("nodes", networkNamespace);
    for (NetworkNode nn : nodes) {
      Element nodeElm = new Element("node", networkNamespace);
      nodeElm.setAttribute("id", String.valueOf(nn.getId()));
      nodeElm.setAttribute("name", nn.getName());
      nodeElm.setAttribute("kind", nn.getKind().toString());

      Element heiElm = marshallHazardEventIntensities(nn);
      nodeElm.addContent(heiElm);
      
      nodesElm.addContent(nodeElm);
    }
    return nodesElm;
  }

  Element marshallHazardEventIntensities(NetworkNode nn) {
    Element heiElm = new Element("hazard-event-intensities", networkNamespace);

    Element riskCategory1Elm = new Element("risk-category-1", networkNamespace);
    Element riskCategory2Elm = new Element("risk-category-2", networkNamespace);
    Element riskCategory3Elm = new Element("risk-category-3", networkNamespace);

    Element recoveryTime1Elm = new Element("recovery-time-1", networkNamespace);
    Element recoveryTime2Elm = new Element("recovery-time-2", networkNamespace);
    Element recoveryTime3Elm = new Element("recovery-time-3", networkNamespace);

    Element typeElm = new Element("type", networkNamespace);

    riskCategory1Elm.setText(String.valueOf(nn.getRiskCategory1()));
    riskCategory2Elm.setText(String.valueOf(nn.getRiskCategory2()));
    riskCategory3Elm.setText(String.valueOf(nn.getRiskCategory3()));

    recoveryTime1Elm.setText(String.valueOf(nn.getRecoveryTime1()));
    recoveryTime2Elm.setText(String.valueOf(nn.getRecoveryTime2()));
    recoveryTime3Elm.setText(String.valueOf(nn.getRecoveryTime3()));

    typeElm.setText(nn.getType().toString());

    heiElm.addContent(riskCategory1Elm);
    heiElm.addContent(riskCategory2Elm);
    heiElm.addContent(riskCategory3Elm);
    heiElm.addContent(recoveryTime1Elm);
    heiElm.addContent(recoveryTime2Elm);
    heiElm.addContent(recoveryTime3Elm);
    heiElm.addContent(typeElm);

    return heiElm;
  }

  Element marshallEdges(Collection<NetworkEdge> edges) {
    Element edgesElm = new Element("edges", networkNamespace);
    for (NetworkEdge ne : edges) {
      Element edgeElm = new Element("edge", networkNamespace);
      edgeElm.setAttribute("source", String.valueOf(ne.getSource().getId()));
      edgeElm.setAttribute("target", String.valueOf(ne.getTarget().getId()));
      edgeElm.setAttribute("purchasingVolume", String.valueOf(ne.getPurchasingVolume()));
      edgesElm.addContent(edgeElm);
    }
    return edgesElm;
  }

  Element marshallInputParams(Map<String, String> inputParams) {
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
