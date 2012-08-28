package com.scirisk.riskanalyzer.backend.service;

import org.jdom2.Element;
import org.springframework.ws.server.endpoint.AbstractJDomPayloadEndpoint;

import com.scirisk.riskanalyzer.backend.domain.CalculateRequest;
import com.scirisk.riskanalyzer.backend.domain.CalculateRequestUnmarshaller;
import com.scirisk.riskanalyzer.backend.domain.CalculateResponse;
import com.scirisk.riskanalyzer.backend.domain.CalculateResponseMarshaller;

public class CalculateHandler extends AbstractJDomPayloadEndpoint {

  CalculationService calculationService;
  CalculateRequestUnmarshaller requestUnmarshaller;
  CalculateResponseMarshaller responseMarshaller;

  protected Element invokeInternal(Element requestElm) throws Exception {
    System.out.println("CALCULATE REQUEST RECEIVED..");
    CalculateRequest calculateRequest = requestUnmarshaller.unmarshall(requestElm);
    CalculateResponse calculateResponse = calculationService.calculate(calculateRequest);
    Element responseElm = responseMarshaller.marshall(calculateResponse);
    System.out.println("CALCULATE REQUEST COMPLETE");
    return responseElm;
  }

  public void setCalculationService(CalculationService calculationService) {
    this.calculationService = calculationService;
  }

  public void setRequestUnmarshaller(CalculateRequestUnmarshaller requestUnmarshaller) {
    this.requestUnmarshaller = requestUnmarshaller;
  }

  public void setResponseMarshaller(CalculateResponseMarshaller responseMarshaller) {
    this.responseMarshaller = responseMarshaller;
  }

}
