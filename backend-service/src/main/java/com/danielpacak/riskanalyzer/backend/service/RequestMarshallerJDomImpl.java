package com.danielpacak.riskanalyzer.backend.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.transform.stream.StreamSource;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;

import com.danielpacak.riskanalyzer.domain.DistributionChannel;
import com.danielpacak.riskanalyzer.domain.DistributionNetwork;
import com.danielpacak.riskanalyzer.domain.Facility;

public class RequestMarshallerJDomImpl implements RequestMarshaller {

	private Namespace riskAnalyzerNamespace = Namespace
			.getNamespace("http://scirisk.com/xml/ns/risk-analyzer");
	private Namespace networkNamespace = Namespace
			.getNamespace("http://scirisk.com/xml/ns/network");

	@Override
	public StreamSource marshall(CalculateRequest request) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		XMLOutputter xmlOutputter = new XMLOutputter();
		xmlOutputter.output(_marshall(request), out);
		return new StreamSource(new ByteArrayInputStream(out.toByteArray()));
	}

	@Override
	public CalculateRequest unmarshall(StreamSource source) throws IOException {
		try {
			SAXBuilder saxBuilder = new SAXBuilder();
			Document document = saxBuilder.build(source.getInputStream());
			return unmarshall(document.getRootElement());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	Element _marshall(CalculateRequest request) {
		Element calculateRequestElm = new Element("CalculateRequest",
				riskAnalyzerNamespace);

		Element networkElm = marshallNetwork(request.getNetwork());
		Element inputParamsElm = marshallInputParams(request.getInputParams());

		calculateRequestElm.addContent(networkElm);
		calculateRequestElm.addContent(inputParamsElm);

		return calculateRequestElm;
	}

	Element marshallNetwork(DistributionNetwork network) {
		Element networkElm = new Element("Network", riskAnalyzerNamespace);
		Element nodesElm = marshallNodes(network.getNodes());
		Element edgesElm = marshallEdges(network.getEdges());
		networkElm.addContent(nodesElm);
		networkElm.addContent(edgesElm);
		return networkElm;
	}

	Element marshallNodes(Collection<Facility> nodes) {
		Element nodesElm = new Element("nodes", networkNamespace);
		for (Facility nn : nodes) {
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

	Element marshallHazardEventIntensities(Facility nn) {
		Element heiElm = new Element("hazard-event-intensities",
				networkNamespace);

		Element riskCategory1Elm = new Element("risk-category-1",
				networkNamespace);
		Element riskCategory2Elm = new Element("risk-category-2",
				networkNamespace);
		Element riskCategory3Elm = new Element("risk-category-3",
				networkNamespace);

		Element recoveryTime1Elm = new Element("recovery-time-1",
				networkNamespace);
		Element recoveryTime2Elm = new Element("recovery-time-2",
				networkNamespace);
		Element recoveryTime3Elm = new Element("recovery-time-3",
				networkNamespace);

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

	Element marshallEdges(Collection<DistributionChannel> edges) {
		Element edgesElm = new Element("edges", networkNamespace);
		for (DistributionChannel ne : edges) {
			Element edgeElm = new Element("edge", networkNamespace);
			edgeElm.setAttribute("source",
					String.valueOf(ne.getSource().getId()));
			edgeElm.setAttribute("target",
					String.valueOf(ne.getTarget().getId()));
			edgeElm.setAttribute("purchasingVolume",
					String.valueOf(ne.getPurchasingVolume()));
			edgesElm.addContent(edgeElm);
		}
		return edgesElm;
	}

	Element marshallInputParams(Map<String, String> inputParams) {
		Element inputParamsElm = new Element("InputParams",
				riskAnalyzerNamespace);
		for (Entry<String, String> entry : inputParams.entrySet()) {
			Element paramElm = new Element("Parameter", riskAnalyzerNamespace);
			paramElm.setAttribute("name", entry.getKey());
			paramElm.setAttribute("value", entry.getValue());
			inputParamsElm.addContent(paramElm);
		}
		return inputParamsElm;
	}

	private CalculateRequest unmarshall(Element calculateRequestElm) {

		System.out.println("Unmarshalling 'CalculateRequest' element: "
				+ calculateRequestElm);
		Element inputParamsElm = calculateRequestElm.getChild("InputParams",
				riskAnalyzerNamespace);
		Element networkElm = calculateRequestElm.getChild("Network",
				riskAnalyzerNamespace);
		unmarshallNetwork(networkElm);

		Map<String, String> inputParams = unmarshallInputParams(inputParamsElm);
		System.out.println("inputParams: " + inputParams);

		CalculateRequest request = new CalculateRequest();
		request.setInputParams(inputParams);
		return request;
	}

	private void unmarshallNetwork(Element networkElm) {
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
					+ ", kind: " + nodeElm.getAttributeValue("kind") + "]");
		}
	}

	void unmarshallEdges(Element edgesElm) {
		System.out.println("Unmarshalling 'edges' element: " + edgesElm);
		List<Element> edges = edgesElm.getChildren("edge", networkNamespace);
		for (Element edgeElm : edges) {
			System.out.println("[source: "
					+ edgeElm.getAttributeValue("source") + ", target: "
					+ edgeElm.getAttributeValue("target")
					+ ", purchasingVolume: "
					+ edgeElm.getAttributeValue("purchasingVolume") + "]");
		}
	}

	Map<String, String> unmarshallInputParams(Element inputParamsElm) {
		Map<String, String> inputParams = new HashMap<String, String>();

		List<Element> parameters = inputParamsElm.getChildren("Parameter",
				riskAnalyzerNamespace);
		for (Element inputParamElm : parameters) {
			inputParams.put(inputParamElm.getAttributeValue("name"),
					inputParamElm.getAttributeValue("value"));
		}
		return inputParams;
	}

}
