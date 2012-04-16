package com.scirisk.riskanalyzer.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

@SuppressWarnings("serial")
public class Network implements Serializable {

  private Collection<NetworkNode> nodes = new ArrayList<NetworkNode>();
  private Collection<NetworkEdge> edges = new ArrayList<NetworkEdge>();
  
  public Network() {}

  public Network(Collection<NetworkNode> nodes, Collection<NetworkEdge> edges) {
    this.nodes = nodes;
    this.edges = edges;
  }

  public Collection<NetworkNode> getNodes() {
    return nodes;
  }

  public void setNodes(Collection<NetworkNode> nodes) {
    this.nodes = nodes;
  }

  public Collection<NetworkEdge> getEdges() {
    return edges;
  }

  public void setEdges(Collection<NetworkEdge> edges) {
    this.edges = edges;
  }

}