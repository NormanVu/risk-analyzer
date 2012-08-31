package com.scirisk.riskanalyzer.repository.google;

import java.util.ArrayList;
import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.scirisk.riskanalyzer.domain.Facility;
import com.scirisk.riskanalyzer.domain.Facility.Kind;
import com.scirisk.riskanalyzer.domain.Facility.Type;
import com.scirisk.riskanalyzer.repository.NetworkNodeManager;

public class NetworkNodeManagerGoogleImpl implements NetworkNodeManager {

  private static final String NODE_ENTITY_KIND = "NetworkNode";

  public List<Facility> findAll() {
    DatastoreService service = DatastoreServiceFactory.getDatastoreService();
    Query q = new Query(NODE_ENTITY_KIND);
    PreparedQuery pq = service.prepare(q);
    ArrayList<Facility> nodes = new ArrayList<Facility>();
    for (Entity nodeEntity : pq.asIterable()) {
      Facility node = map(nodeEntity);
      nodes.add(node);
    }
    return nodes;
  }

  public void delete(final String nodeId) {
    DatastoreService service = DatastoreServiceFactory.getDatastoreService();
    Key nodeKey = KeyFactory.createKey(NODE_ENTITY_KIND, nodeId);
    service.beginTransaction();
    service.delete(nodeKey);
    service.getCurrentTransaction().commit();
  }


  public Facility save(Facility node) {
    DatastoreService service = DatastoreServiceFactory.getDatastoreService();
    Entity nodeEntity = null;
    if (node.getId() != null) {
      Key nodeKey = KeyFactory.createKey(NODE_ENTITY_KIND, node.getId());
      try {
        nodeEntity = service.get(nodeKey);
      } catch (EntityNotFoundException e) {
        throw new IllegalArgumentException("Cannot find network node entity [" + node.getId() + "].");
      }
    } else {
      nodeEntity = new Entity(NODE_ENTITY_KIND);
    }
    nodeEntity.setProperty("name", node.getName());
    nodeEntity.setProperty("kind", node.getKind().toString());
    nodeEntity.setProperty("description", node.getDescription());
    nodeEntity.setProperty("address", node.getAddress());
    nodeEntity.setProperty("latitude", node.getLatitude());
    nodeEntity.setProperty("longitude", node.getLongitude());
    nodeEntity.setProperty("riskCategory1", node.getRiskCategory1());
    nodeEntity.setProperty("riskCategory2", node.getRiskCategory2());
    nodeEntity.setProperty("riskCategory3", node.getRiskCategory3());
    nodeEntity.setProperty("recoveryTime1", node.getRecoveryTime1());
    nodeEntity.setProperty("recoveryTime2", node.getRecoveryTime2());
    nodeEntity.setProperty("recoveryTime3", node.getRecoveryTime3());
    nodeEntity.setProperty("type", node.getType().toString());
    service.beginTransaction();
    Key generatedKey = service.put(nodeEntity);
    service.getCurrentTransaction().commit();
    node.setId(generatedKey.toString());
    return node;
  }

  public Facility findOne(String nodeId) {
    Key nodeKey = KeyFactory.createKey(NODE_ENTITY_KIND, nodeId);
    DatastoreService service = DatastoreServiceFactory.getDatastoreService();
    try {
      Entity nodeEntity = service.get(nodeKey);
      Facility node = map(nodeEntity);
      return node;
    } catch (EntityNotFoundException e) {
      throw new IllegalArgumentException("Cannot find network node entity [" + nodeId + "].");
    }
  }

  Facility map(Entity nodeEntity) {
    Facility node = new Facility();
    node.setId(nodeEntity.getKey().toString());
    node.setName((String) nodeEntity.getProperty("name"));
    node.setKind(Kind.valueOf((String) nodeEntity.getProperty("kind")));
    node.setDescription((String) nodeEntity.getProperty("description"));
    node.setAddress((String) nodeEntity.getProperty("address"));
    node.setLatitude((Double) nodeEntity.getProperty("latitude"));
    node.setLongitude((Double) nodeEntity.getProperty("longitude"));
    node.setRiskCategory1((Double) nodeEntity.getProperty("riskCategory1"));
    node.setRiskCategory2((Double) nodeEntity.getProperty("riskCategory2"));
    node.setRiskCategory3((Double) nodeEntity.getProperty("riskCategory3"));
    node.setRecoveryTime1((Double) nodeEntity.getProperty("recoveryTime1"));
    node.setRecoveryTime2((Double) nodeEntity.getProperty("recoveryTime2"));
    node.setRecoveryTime3((Double) nodeEntity.getProperty("recoveryTime3"));
    node.setType(Type.valueOf((String) nodeEntity.getProperty("type")));

    return node;
  }

}
