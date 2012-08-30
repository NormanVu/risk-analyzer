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
import com.scirisk.riskanalyzer.domain.NetworkEdge;
import com.scirisk.riskanalyzer.domain.NetworkNode;
import com.scirisk.riskanalyzer.repository.NetworkEdgeManager;

public class NetworkEdgeManagerGoogleImpl implements NetworkEdgeManager {

  public NetworkEdge save(NetworkEdge edge, String sourceId, String targetId) {
    DatastoreService service = DatastoreServiceFactory.getDatastoreService();
    Key sourceKey = KeyFactory.createKey("NetworkNode", sourceId);
    Key targetKey = KeyFactory.createKey("NetworkNode", targetId);

    Entity entity = null;
    if (edge.getId() != null) {
      Key edgeKey = KeyFactory.createKey("NetworkEdge", edge.getId());
      try {
        entity = service.get(edgeKey);
      } catch (EntityNotFoundException e) {
        throw new IllegalArgumentException("Cannot find network edge entity [" + edge.getId() + "].");
      }
    } else {
      entity = new Entity("NetworkEdge");
    }
    
    entity.setProperty("purchasingVolume", edge.getId());
    entity.setProperty("sourceId", sourceKey);
    entity.setProperty("targetId", targetKey);
    service.beginTransaction();
    service.put(entity);
    service.getCurrentTransaction().commit();
    return edge;
  }

  public NetworkEdge findOne(String edgeId) {
    DatastoreService service = DatastoreServiceFactory.getDatastoreService();
    Key edgeKey = KeyFactory.createKey("NetworkEdge", edgeId);
    try {
      Entity edgeEntity = service.get(edgeKey);
      NetworkEdge edge = createEdge(edgeEntity);

      Key sourceKey = (Key) edgeEntity.getProperty("sourceId");
      Key targetKey = (Key) edgeEntity.getProperty("targetId");

      Entity sourceEntity = service.get(sourceKey);
      Entity targetEntity = service.get(targetKey);

      edge.setSource(createNode(sourceEntity));
      edge.setTarget(createNode(targetEntity));
      return edge;
    } catch (EntityNotFoundException e) {
      throw new IllegalArgumentException("Cannot find network edge entity [" + edgeId + "].");
    }
  }

  public void delete(final String edgeId) {
    DatastoreService service = DatastoreServiceFactory.getDatastoreService();
    Key edgeKey = KeyFactory.createKey("NetworkEdge", edgeId);
    service.beginTransaction();
    service.delete(edgeKey);
    service.getCurrentTransaction().commit();
  }

  public List<NetworkEdge> findAll() {
    DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
    Query q = new Query("NetworkEdge");
    PreparedQuery pq = ds.prepare(q);
    ArrayList<NetworkEdge> edges = new ArrayList<NetworkEdge>();
    for (Entity edgeEntity : pq.asIterable()) {
      try {
        NetworkEdge edge = createEdge(edgeEntity);

        Key sourceKey = (Key) edgeEntity.getProperty("sourceId");
        Key targetKey = (Key) edgeEntity.getProperty("targetId");

        Entity sourceEntity = ds.get(sourceKey);
        Entity targetEntity = ds.get(targetKey);

        edge.setSource(createNode(sourceEntity));
        edge.setTarget(createNode(targetEntity));

        edges.add(edge);
      } catch (EntityNotFoundException e) {
        throw new RuntimeException(e);
      }
    }
    return edges;
  }

  private NetworkEdge createEdge(Entity edgeEntity) {
    NetworkEdge edge = new NetworkEdge();
    edge.setId(edgeEntity.getKey().toString());
    edge.setPurchasingVolume((Double) edgeEntity.getProperty("purchasingVolume"));
    return edge;
  }
  

  private NetworkNode createNode(Entity nodeEntity) {
    NetworkNode n = new NetworkNode();
    n.setId(nodeEntity.getKey().toString());
    n.setName((String) nodeEntity.getProperty("name"));
    n.setLatitude((Double) nodeEntity.getProperty("latitude"));
    n.setLongitude((Double) nodeEntity.getProperty("longitude"));
    return n;
  }


}
