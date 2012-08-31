package com.scirisk.riskanalyzer.service;

import java.io.OutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.scirisk.riskanalyzer.domain.DistributionNetwork;
import com.scirisk.riskanalyzer.domain.DistributionChannel;
import com.scirisk.riskanalyzer.domain.Facility;

// jaxp dom marshaller
public class NetworkMarshallerDomImpl implements NetworkMarshaller {

  public void marshall(DistributionNetwork network, OutputStream os) {
    try {
      DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
      Document doc = docBuilder.newDocument();

      TransformerFactory transformerFactory = TransformerFactory.newInstance();
      Transformer transformer = transformerFactory.newTransformer();
      transformer.setOutputProperty(OutputKeys.INDENT, "yes");
      
      
      Element networkElement = doc.createElement("network");
      networkElement.setAttribute("version", "1.0");
      
      // marshall nodes
      Element nodesElement = doc.createElement("nodes");
      for (Facility nn : network.getNodes()) {
        Element nodeElement = doc.createElement("node");
        nodeElement.setAttribute("id", String.valueOf(nn.getId()));
        nodeElement.setAttribute("name", nn.getName());
        nodeElement.setAttribute("kind", String.valueOf(nn.getKind()));
        
        // marshall address
        Element addressElement = doc.createElement("address");
        Element innerAddressElm = doc.createElement("address");
        innerAddressElm.appendChild(doc.createTextNode(nn.getAddress()));
        Element latElement = doc.createElement("lat");
        Element lngElement = doc.createElement("lng");
        latElement.appendChild(doc.createTextNode(String.valueOf(nn.getLatitude())));
        lngElement.appendChild(doc.createTextNode(String.valueOf(nn.getLongitude())));
        
        addressElement.appendChild(innerAddressElm);
        addressElement.appendChild(latElement);
        addressElement.appendChild(lngElement);
        
        
        
        
        // marshall hazar event intensities
        Element hazardElement = doc.createElement("hazard-event-intensities");
        Element riskCat1Element = doc.createElement("risk-category-1");
        Element riskCat2Element = doc.createElement("risk-category-2");
        Element riskCat3Element = doc.createElement("risk-category-3");
        Element recoveryTime1Elm = doc.createElement("recovery-time-1");
        Element recoveryTime2Elm = doc.createElement("recovery-time-2");
        Element recoveryTime3Elm = doc.createElement("recovery-time-3");
        Element typeElement = doc.createElement("type");
        riskCat1Element.appendChild(doc.createTextNode(String.valueOf(nn.getRiskCategory1())));
        riskCat2Element.appendChild(doc.createTextNode(String.valueOf(nn.getRiskCategory2())));
        riskCat3Element.appendChild(doc.createTextNode(String.valueOf(nn.getRiskCategory3())));
        recoveryTime1Elm.appendChild(doc.createTextNode(String.valueOf(nn.getRecoveryTime1())));
        recoveryTime2Elm.appendChild(doc.createTextNode(String.valueOf(nn.getRecoveryTime2())));
        recoveryTime3Elm.appendChild(doc.createTextNode(String.valueOf(nn.getRecoveryTime3())));
        typeElement.appendChild(doc.createTextNode(String.valueOf(nn.getType())));

        hazardElement.appendChild(riskCat1Element);
        hazardElement.appendChild(riskCat2Element);
        hazardElement.appendChild(riskCat3Element);
        hazardElement.appendChild(recoveryTime1Elm);
        hazardElement.appendChild(recoveryTime2Elm);
        hazardElement.appendChild(recoveryTime3Elm);
        hazardElement.appendChild(typeElement);
        
        
        nodeElement.appendChild(addressElement);
        nodeElement.appendChild(hazardElement);
        
        nodesElement.appendChild(nodeElement);
      }
      
      
      
      // marshall edges
      Element edgesElement = doc.createElement("edges");
      for (DistributionChannel ne : network.getEdges()) {
        Element edgeElement = doc.createElement("edge");
        edgeElement.setAttribute("purchasingVolume", String.valueOf(ne.getPurchasingVolume()));
        edgeElement.setAttribute("source", String.valueOf(ne.getSource().getId()));
        edgeElement.setAttribute("target", String.valueOf(ne.getTarget().getId()));
        edgesElement.appendChild(edgeElement);
      }
      
      networkElement.appendChild(nodesElement);
      networkElement.appendChild(edgesElement);
      
      doc.appendChild(networkElement);
      
      

      DOMSource source = new DOMSource(doc);
      StreamResult result = new StreamResult(os);
      transformer.transform(source, result);
    } catch (ParserConfigurationException e) {
      
    } catch (TransformerConfigurationException e) {
      
    } catch (TransformerException e) {
      
    }
  }
}