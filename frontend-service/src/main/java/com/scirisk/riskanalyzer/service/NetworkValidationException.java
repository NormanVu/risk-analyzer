package com.scirisk.riskanalyzer.service;

import java.util.List;

@SuppressWarnings("serial")
public class NetworkValidationException extends Exception {

  private List<ValidationError> validationErrors;

  public NetworkValidationException(List<ValidationError> validationErrors) {
    this.validationErrors = validationErrors;
  }

  public NetworkValidationException(String message, List<ValidationError> validationErrors) {
    super(message);
    this.validationErrors = validationErrors;
  }

  public List<ValidationError> getValidationErrors() {
    return validationErrors;
  }

}