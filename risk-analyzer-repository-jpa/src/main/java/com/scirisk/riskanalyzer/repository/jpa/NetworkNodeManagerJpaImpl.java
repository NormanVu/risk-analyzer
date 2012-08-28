package com.scirisk.riskanalyzer.repository.jpa;

import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import com.scirisk.riskanalyzer.domain.NetworkNode;
import com.scirisk.riskanalyzer.repository.NetworkNodeManager;

public class NetworkNodeManagerJpaImpl implements NetworkNodeManager {

	private EntityManagerFactory emf;

	public NetworkNodeManagerJpaImpl(EntityManagerFactory emf) {
		this.emf = emf;
	}

	public NetworkNode save(final NetworkNode node) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		node.setId(isBlank(node.getId()) ? UUID.randomUUID().toString() : node
				.getId());
		em.merge(node);
		em.flush();
		em.getTransaction().commit();
		return node;
	}

	boolean isBlank(String string) {
		return "".equals(string);
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

	public void delete(final String nodeId) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		NetworkNode nn = em.find(NetworkNode.class, nodeId);
		em.remove(nn);
		em.getTransaction().commit();
	}

	public NetworkNode findOne(final String nodeId) {
		EntityManager em = emf.createEntityManager();
		NetworkNode node = em.find(NetworkNode.class, nodeId);
		return node;
	}

}
