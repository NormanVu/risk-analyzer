package com.scirisk.riskanalyzer.repository.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import com.scirisk.riskanalyzer.domain.NetworkEdge;
import com.scirisk.riskanalyzer.domain.NetworkNode;
import com.scirisk.riskanalyzer.repository.NetworkEdgeManager;

public class NetworkEdgeManagerJpaImpl implements NetworkEdgeManager {

	private EntityManagerFactory emf;

	public NetworkEdgeManagerJpaImpl(EntityManagerFactory emf) {
		this.emf = emf;
	}

	public void save(NetworkEdge edge, Long sourceId, Long targetId) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();

		NetworkNode source = em.find(NetworkNode.class, sourceId);
		NetworkNode target = em.find(NetworkNode.class, targetId);

		edge.setSource(source);
		edge.setTarget(target);

		em.merge(edge);

		em.getTransaction().commit();
	}

	public List<NetworkEdge> findAll() {
		EntityManager em = emf.createEntityManager();
		final String queryString = "SELECT o FROM "
				+ NetworkEdge.class.getName() + " o";
		Query q = em.createQuery(queryString);
		em.getTransaction().begin();
		@SuppressWarnings("unchecked")
		List<NetworkEdge> nodes = q.getResultList();
		em.getTransaction().commit();
		return nodes;
	}

	public void delete(final Long edgeId) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		NetworkEdge edge = em.find(NetworkEdge.class, edgeId);
		em.remove(edge);
		em.getTransaction().commit();
	}

	public NetworkEdge findOne(final Long edgeId) {
		EntityManager em = emf.createEntityManager();
		return em.find(NetworkEdge.class, edgeId);
	}

}
