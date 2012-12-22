package com.scirisk.riskanalyzer.service;

import java.io.Serializable;

// represents network validation error
@SuppressWarnings("serial")
public class ValidationError implements Serializable {

  private String category; // error, warning or fatalError
  private String message;
  private Long lineNumer;

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public Long getLineNumer() {
    return lineNumer;
  }

  public void setLineNumer(Long lineNumer) {
    this.lineNumer = lineNumer;
  }

}