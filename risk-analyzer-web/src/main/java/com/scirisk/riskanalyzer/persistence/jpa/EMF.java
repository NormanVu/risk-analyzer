package com.scirisk.riskanalyzer.persistence.jpa;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EMF {

  /**
   * The name of JPA persistence unit in the /META-INF/persistence.xml
   * configuration file.
   */
  public static final String PERSISTENCE_UNIT_NAME = "risk-analyzer-pu";

  private static EntityManagerFactory INSTANCE =  Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);

  private EMF() {}

  public static EntityManagerFactory get() {
    return INSTANCE;
  }

}