package com.danielpacak.riskanalyzer.frontend.service.simple;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

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
import org.w3c.dom.NodeList;

import com.danielpacak.riskanalyzer.domain.DistributionChannel;
import com.danielpacak.riskanalyzer.domain.DistributionNetwork;
import com.danielpacak.riskanalyzer.domain.Facility;
import com.danielpacak.riskanalyzer.domain.Facility.Kind;
import com.danielpacak.riskanalyzer.domain.Facility.Type;
import com.danielpacak.riskanalyzer.frontend.service.api.NetworkMarshaller;
import com.danielpacak.riskanalyzer.frontend.service.api.NetworkValidationException;

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

	private DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();

	public DistributionNetwork unmarshall(InputStream is) throws IOException, NetworkValidationException {
		try {
			DocumentBuilder builder = f.newDocumentBuilder();
			Document document = builder.parse(is);
			Map<String, Facility> nodesMap = new HashMap<String, Facility>();
			DistributionNetwork network = new DistributionNetwork();
			Element networkElement = document.getDocumentElement(); // root
																	// element
																	// is named
																	// network

			// parse nodes element
			Element nodesElement = (Element) networkElement.getElementsByTagName("nodes").item(0); // should
																									// be
																									// safe
																									// with
																									// XSD
																									// validation
			NodeList nodeList = nodesElement.getElementsByTagName("node");
			for (int i = 0; i < nodeList.getLength(); i++) {
				Element nodeElement = (Element) nodeList.item(i);
				Facility nn = parseNode(nodeElement);
				network.getNodes().add(nn);

				nodesMap.put(nn.getId(), nn);
			}

			// parse edges element
			Element edgesElement = (Element) networkElement.getElementsByTagName("edges").item(0); // should
																									// be
																									// safe
																									// with
																									// XSD
																									// validation

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
		Element innerAddressElm = (Element) addressElement.getElementsByTagName("address").item(0);
		Element latElement = (Element) addressElement.getElementsByTagName("lat").item(0);
		Element lngElement = (Element) addressElement.getElementsByTagName("lng").item(0);

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