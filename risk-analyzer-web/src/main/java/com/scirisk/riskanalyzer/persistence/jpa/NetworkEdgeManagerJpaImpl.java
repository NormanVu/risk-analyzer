package com.scirisk.riskanalyzer.persistence.jpa;

import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.scirisk.riskanalyzer.domain.NetworkEdge;
import com.scirisk.riskanalyzer.domain.NetworkNode;
import com.scirisk.riskanalyzer.persistence.NetworkEdgeManager;

public class NetworkEdgeManagerJpaImpl implements NetworkEdgeManager {

  public void save(Long edgeId, Double purchasingVolume, Long sourceId, Long targetId) {
    EntityManager em = EMF.get().createEntityManager();
    em.getTransaction().begin();

    NetworkEdge edge = edgeId != null ? em.find(NetworkEdge.class, edgeId) : new NetworkEdge();

    edge.setPurchasingVolume(purchasingVolume);

    NetworkNode srcNode = em.find(NetworkNode.class, sourceId);
    NetworkNode destNode = em.find(NetworkNode.class, targetId);

    edge.setSourceNode(srcNode);
    edge.setTargetNode(destNode);

    em.persist(edge);

    em.getTransaction().commit();
  }

  public Collection<NetworkEdge> findAll() {
    EntityManager em = EMF.get().createEntityManager();
    final String queryString = "SELECT o FROM " + NetworkEdge.class.getName() + " o";
    Query q = em.createQuery(queryString);
    em.getTransaction().begin();
    @SuppressWarnings("unchecked")
    List<NetworkEdge> nodes = q.getResultList();
    em.getTransaction().commit();
    return nodes;
  }

  public void delete(final Long edgeId) {
    EntityManager em = EMF.get().createEntityManager();
    em.getTransaction().begin();
    NetworkEdge edge = em.find(NetworkEdge.class, edgeId);
    em.remove(edge);
    em.getTransaction().commit();
  }

  public NetworkEdge read(final Long edgeId) {
    EntityManager em = EMF.get().createEntityManager();
    NetworkEdge edge = em.find(NetworkEdge.class, edgeId);
    return edge;
  }

}