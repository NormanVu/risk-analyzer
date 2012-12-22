package com.scirisk.riskanalyzer.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.scirisk.riskanalyzer.domain.DistributionNetwork;
import com.scirisk.riskanalyzer.domain.DistributionChannel;
import com.scirisk.riskanalyzer.domain.Facility;
import com.scirisk.riskanalyzer.domain.Facility.Kind;
import com.scirisk.riskanalyzer.domain.Facility.Type;

public class NetworkParserDomImpl implements NetworkParser {

  /** Logger for this class. */
  private static Logger log = Logger.getLogger(NetworkParserDomImpl.class.getName());

  private DocumentBuilderFactory f;

  public NetworkParserDomImpl() {
    this.f = DocumentBuilderFactory.newInstance();

  }

  public DistributionNetwork parse(InputStream is) throws IOException {
    try {
      DocumentBuilder builder = f.newDocumentBuilder();
      Document document = builder.parse(is);
      Map<String, Facility> nodesMap = new HashMap<String, Facility>();
      DistributionNetwork network = new DistributionNetwork();
      Element networkElement = document.getDocumentElement(); // root element is named network
      
      // parse nodes element
      Element nodesElement = (Element) networkElement.getElementsByTagName("nodes").item(0); // should be safe with XSD validation
      NodeList nodeList = nodesElement.getElementsByTagName("node");
      for (int i = 0; i < nodeList.getLength(); i++) {
        Element nodeElement = (Element) nodeList.item(i);
        Facility nn = parseNode(nodeElement);
        network.getNodes().add(nn);
        
        nodesMap.put(nn.getId(), nn);
      }

      // parse edges element
      Element edgesElement = (Element) networkElement.getElementsByTagName("edges").item(0); // should be safe with XSD validation

      NodeList edgeList = edgesElement.getElementsByTagName("edge");
      for (int i = 0; i < edgeList.getLength(); i++) {
        Element edgeElement = (Element) edgeList.item(i);
        DistributionChannel ne = parseEdge(edgeElement, nodesMap);
        network.getEdges().add(ne);
      }

      return network;
    } catch (Exception e) {
      // TODO REVIEW EXCEPTIONS HANDLING
      e.printStackTrace();
      throw new IllegalStateException(e);
    }
  }

  private Facility parseNode(final Element nodeElement) {
    String idAttr = nodeElement.getAttribute("id");
    String kindAttr = nodeElement.getAttribute("kind");
    String nameAttr = nodeElement.getAttribute("name");

    Facility nn = new Facility();
    nn.setId(idAttr);
    nn.setKind(Kind.valueOf(kindAttr));
    nn.setName(nameAttr);

    // parse address (lat, lng)
    Element addressElement = (Element) nodeElement.getElementsByTagName("address").item(0);
    log.finest("addressElement.nodeName: " + addressElement.getNodeName());
    Element innerAddressElm = (Element) addressElement.getElementsByTagName("address").item(0);
    Element latElement = (Element) addressElement.getElementsByTagName("lat").item(0);
    Element lngElement = (Element) addressElement.getElementsByTagName("lng").item(0);
    log.finest("latElement.nodeName: " + latElement.getNodeName());
    log.finest("latElement.firstChild.nodeValue: " + latElement.getFirstChild().getNodeValue());
    log.finest("lngElement.nodeName: " + lngElement.getNodeName());
    log.finest("lngElement.firstChild.nodeValue: " + lngElement.getFirstChild().getNodeValue());

    nn.setAddress(innerAddressElm.getFirstChild().getNodeValue());
    nn.setLatitude(Double.valueOf(latElement.getFirstChild().getNodeValue()));
    nn.setLongitude(Double.valueOf(lngElement.getFirstChild().getNodeValue()));

    // parse hazard-event-intensities element
    Element heiElement = (Element) nodeElement.getElementsByTagName("hazard-event-intensities").item(0);
    Element riskCat1Element = (Element) heiElement.getElementsByTagName("risk-category-1").item(0);
    Element riskCat2Element = (Element) heiElement.getElementsByTagName("risk-category-2").item(0);
    Element riskCat3Element = (Element) heiElement.getElementsByTagName("risk-category-3").item(0);
    Element recoveryTime1Elm = (Element) heiElement.getElementsByTagName("recovery-time-1").item(0);
    Element recoveryTime2Elm = (Element) heiElement.getElementsByTagName("recovery-time-2").item(0);
    Element recoveryTime3Elm = (Element) heiElement.getElementsByTagName("recovery-time-3").item(0);
    Element typeElement = (Element) heiElement.getElementsByTagName("type").item(0);

    nn.setRiskCategory1(Double.valueOf(riskCat1Element.getFirstChild().getNodeValue()));
    nn.setRiskCategory2(Double.valueOf(riskCat2Element.getFirstChild().getNodeValue()));
    nn.setRiskCategory3(Double.valueOf(riskCat3Element.getFirstChild().getNodeValue()));
    nn.setRecoveryTime1(Double.valueOf(recoveryTime1Elm.getFirstChild().getNodeValue()));
    nn.setRecoveryTime2(Double.valueOf(recoveryTime2Elm.getFirstChild().getNodeValue()));
    nn.setRecoveryTime3(Double.valueOf(recoveryTime3Elm.getFirstChild().getNodeValue()));
    nn.setType(Type.valueOf(typeElement.getFirstChild().getNodeValue()));

    return nn;
  }

  private DistributionChannel parseEdge(final Element edgeElement, final Map<String, Facility> nodesMap) {
    String sourceId = edgeElement.getAttribute("source");
    String targetId = edgeElement.getAttribute("target");
    String purchasingVolumeAttr = edgeElement.getAttribute("purchasingVolume");
    DistributionChannel ne = new DistributionChannel();

    ne.setPurchasingVolume(Double.valueOf(purchasingVolumeAttr));
    // TODO CHECK THAT NODE EXISTS
    ne.setSource(nodesMap.get(sourceId));
    ne.setTarget(nodesMap.get(targetId));

    return ne;
  }

}