package com.scirisk.riskanalyzer.persistence.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;

import com.scirisk.riskanalyzer.domain.NetworkNode;
import com.scirisk.riskanalyzer.persistence.NetworkNodeManager;

public class NetworkNodeManagerJpaImpl implements NetworkNodeManager {

	@Autowired
	EntityManagerFactory emf;

	public Long save(final NetworkNode node) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.merge(node);
		em.flush();
		em.getTransaction().commit();
		return node.getId();
	}

	public List<NetworkNode> findAll() {
		EntityManager em = emf.createEntityManager();
		final String queryString = "SELECT o FROM "
				+ NetworkNode.class.getName() + " o";
		Query q = em.createQuery(queryString);
		em.getTransaction().begin();
		@SuppressWarnings("unchecked")
		List<NetworkNode> nodes = q.getResultList();
		em.getTransaction().commit();
		return nodes;
	}

	public void delete(final Long nodeId) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		NetworkNode nn = em.find(NetworkNode.class, nodeId);
		em.remove(nn);
		em.getTransaction().commit();
	}

	public NetworkNode findOne(final Long nodeId) {
		EntityManager em = emf.createEntityManager();
		NetworkNode node = em.find(NetworkNode.class, nodeId);
		return node;
	}

}
