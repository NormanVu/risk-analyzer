package com.scirisk.riskanalyzer.repository.jpa;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;

import com.scirisk.riskanalyzer.domain.Network;
import com.scirisk.riskanalyzer.domain.NetworkEdge;
import com.scirisk.riskanalyzer.domain.NetworkNode;
import com.scirisk.riskanalyzer.repository.NetworkManager;

public class NetworkManagerJpaImpl implements NetworkManager {

	private EntityManagerFactory emf;

	@PersistenceUnit
	public void setEntityManagerFactory(EntityManagerFactory emf) {
		this.emf = emf;
	}

	@SuppressWarnings("unchecked")
	public Network read() {
		EntityManager em = emf.createEntityManager();
		Query nodeQuery = em.createQuery("SELECT o FROM "
				+ NetworkNode.class.getName() + " o");
		Query edgeQuery = em.createQuery("SELECT o FROM "
				+ NetworkEdge.class.getName() + " o");
		em.getTransaction().begin();

		List<NetworkNode> nodes = nodeQuery.getResultList();
		List<NetworkEdge> edges = edgeQuery.getResultList();

		em.getTransaction().commit();

		Network network = new Network();
		network.setNodes(nodes);
		network.setEdges(edges);

		return network;
	}

	// TODO ADD OPTION TO APPEND NODES
	public void save(final Network network) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.createQuery("DELETE FROM " + NetworkEdge.class.getName())
				.executeUpdate();
		em.createQuery("DELETE FROM " + NetworkNode.class.getName())
				.executeUpdate();

		Map<Long, Long> nodeIdMap = new HashMap<Long, Long>();

		// persist nodes and populate nodeIdMap
		for (NetworkNode nn : network.getNodes()) {
			Long fakeId = nn.getId();
			nn.setId(null);
			em.persist(nn);
			em.flush();
			Long generatedId = nn.getId();
			nodeIdMap.put(fakeId, generatedId);
		}

		// persist edges with references to nodes created in the same
		// transaction
		for (NetworkEdge ne : network.getEdges()) {
			Long sourceId = nodeIdMap.get(ne.getSource().getId());
			Long targetId = nodeIdMap.get(ne.getTarget().getId());
			NetworkNode source = em.find(NetworkNode.class, sourceId);
			NetworkNode target = em.find(NetworkNode.class, targetId);
			ne.setSource(source);
			ne.setTarget(target);
			em.persist(ne);
		}

		em.getTransaction().commit();
	}

}