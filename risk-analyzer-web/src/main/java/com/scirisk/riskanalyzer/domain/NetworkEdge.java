package com.scirisk.riskanalyzer.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity()
@SuppressWarnings("serial")
public class NetworkEdge implements Serializable {

  private Long id;
  private Double purchasingVolume;
  private NetworkNode sourceNode;
  private NetworkNode targetNode;

  @Id()
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Double getPurchasingVolume() {
    return purchasingVolume;
  }

  public void setPurchasingVolume(Double purchasingVolume) {
    this.purchasingVolume = purchasingVolume;
  }

  @OneToOne()
  public NetworkNode getSourceNode() {
    return sourceNode;
  }

  public void setSourceNode(NetworkNode srcNode) {
    this.sourceNode = srcNode;
  }

  @OneToOne()
  public NetworkNode getTargetNode() {
    return targetNode;
  }

  public void setTargetNode(NetworkNode destNode) {
    this.targetNode = destNode;
  }

}