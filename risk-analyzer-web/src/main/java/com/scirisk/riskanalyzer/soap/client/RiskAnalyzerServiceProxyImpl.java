package com.scirisk.riskanalyzer.soap.client;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;
import org.springframework.ws.client.core.WebServiceTemplate;

public class RiskAnalyzerServiceProxyImpl implements RiskAnalyzerServiceProxy {


	private String endpointUrl;
	private Namespace riskAnalyzerNamespace = Namespace.getNamespace("http://scirisk.com/xml/ns/risk-analyzer");
  private Namespace networkNamespace = Namespace.getNamespace("http://scirisk.com/xml/ns/network");
  CalculateRequestMarshaller requestMarshaller = new CalculateRequestMarshaller();
  CalculateResponseUnmarshaller responseUnmarshaller = new CalculateResponseUnmarshaller();


	public RiskAnalyzerServiceProxyImpl(String endpointUrl) {
		this.endpointUrl = endpointUrl;
	}

	public CalculateResponse calculate(CalculateRequest request) throws Exception {
	  
		WebServiceTemplate template = new WebServiceTemplate();
		Element calculateRequestElm = requestMarshaller.marshall(request);
		
		StreamSource source = createStreamSource(calculateRequestElm);
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		StreamResult result = new StreamResult(out);
		
	  template.sendSourceAndReceiveToResult(this.endpointUrl, source, result);
	  
	  System.out.println(new String(out.toByteArray()));
	  SAXBuilder builder = new SAXBuilder();
	  Document document = builder.build(new ByteArrayInputStream(out.toByteArray()));
		
	  CalculateResponse response = responseUnmarshaller.unmarshall(document.getRootElement());
		return response;
	}

	  private StreamSource createStreamSource(Element document) throws IOException {
		  
		  ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		  XMLOutputter outputter = new XMLOutputter();
		  outputter.output(document, buffer);
		  
		  StreamSource source = new StreamSource(new ByteArrayInputStream(buffer.toByteArray()));
		  
		  return source;
	  }



}
