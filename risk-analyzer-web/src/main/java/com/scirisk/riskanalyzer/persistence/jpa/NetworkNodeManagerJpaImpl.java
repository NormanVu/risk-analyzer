package com.scirisk.riskanalyzer.persistence.jpa;

import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.scirisk.riskanalyzer.domain.NetworkNode;
import com.scirisk.riskanalyzer.persistence.NetworkNodeManager;

public class NetworkNodeManagerJpaImpl implements NetworkNodeManager {

  public Long save(final NetworkNode node) {
    EntityManager em = EMF.get().createEntityManager();
    em.getTransaction().begin();
    em.merge(node);
    em.flush();
    em.getTransaction().commit();
    return node.getId();
  }

  public Collection<NetworkNode> findAll() {
    EntityManager em = getEntityManager();
    final String queryString = "SELECT o FROM " + NetworkNode.class.getName() + " o";
    Query q = em.createQuery(queryString);
    em.getTransaction().begin();
    @SuppressWarnings("unchecked")
    List<NetworkNode> nodes = q.getResultList();
    em.getTransaction().commit();
    return nodes;
  }

  public void delete(final Long nodeId) {
    EntityManager em = EMF.get().createEntityManager();
    em.getTransaction().begin();
    NetworkNode nn = em.find(NetworkNode.class, nodeId);
    em.remove(nn);
    em.getTransaction().commit();
  }

  public NetworkNode read(final Long nodeId) {
    EntityManager em = getEntityManager();
    NetworkNode node = em.find(NetworkNode.class, nodeId);
    return node;
  }

  protected EntityManager getEntityManager() {
    return EMF.get().createEntityManager();
  }

}